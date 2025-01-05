import atexit
import socket
import uuid

from flask import Flask, request, jsonify
import consul
import requests
import torch
from torchvision import transforms
from PIL import Image
from io import BytesIO
from model import SimpleCNN

SERVICE_NAME = "classification-service"
SERVICE_HOST = "0.0.0.0"
SERVICE_PORT = 5000
SERVICE_ID = SERVICE_NAME + ':' + str(uuid.uuid4())

CONSUL_HOST = 'consul'
CONSUL_PORT = 8500
CONSUL_TOKEN = '9f6e1638-3270-abd4-2bae-a5a16fa13cb4'

app = Flask(__name__)

# Load the trained model
model = SimpleCNN(num_classes=6)
model.load_state_dict(torch.load('model.pth', map_location='cpu'))
model.eval()

# Define the transform for input images
transform = transforms.Compose([
    transforms.Resize((180, 180)),
    transforms.ToTensor(),
])

label_dict = {0: 'BED', 1: 'CAR', 2: 'CHAIR', 3: 'LAMP', 4: 'LAPTOP', 5: 'MOBILE_PHONE'}

c = consul.Consul(host=CONSUL_HOST, port=CONSUL_PORT, token=CONSUL_TOKEN)


def register_with_consul():
    ip_address = socket.gethostbyname(socket.gethostname())

    c.agent.service.register(
        name=SERVICE_NAME,
        service_id=SERVICE_ID,
        address=ip_address,
        port=SERVICE_PORT,
        check=consul.Check.http(
            url=f"http://{ip_address}:{SERVICE_PORT}/health",
            interval="60s",
            timeout="5s"
        )
    )


def deregister_service():
    url = f"http://{CONSUL_HOST}:{CONSUL_PORT}/v1/agent/service/deregister/{SERVICE_ID}"
    headers = {"X-Consul-Token": CONSUL_TOKEN}

    response = requests.put(url, headers=headers)

    if response.status_code == 200:
        print(f"Service {SERVICE_ID} deregistered successfully.")
    else:
        print(f"Failed to deregister service: {response.status_code}, {response.text}")


atexit.register(deregister_service)


def download_image(url):
    response = requests.get(url)
    response.raise_for_status()
    return Image.open(BytesIO(response.content))


@app.route('/health', methods=['GET'])
def health():
    return jsonify({"status": "UP"}), 200


@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()
    if 'url' not in data:
        return jsonify({'error': 'No URL provided'}), 400

    image_url = data['url']

    try:
        image = download_image(image_url)
    except Exception as e:
        return jsonify({'error': str(e)}), 400

    # Preprocess the image
    input_tensor = transform(image).unsqueeze(0)

    # Run the model to get a prediction
    with torch.no_grad():
        output = model(input_tensor)
        predicted_class = output.argmax(1).item()

    return jsonify({'prediction': label_dict[predicted_class]})


if __name__ == "__main__":
    register_with_consul()

    app.run(host=SERVICE_HOST, port=SERVICE_PORT)
