from flask import Flask, request, jsonify
import requests
import torch
from torchvision import transforms
from PIL import Image
from io import BytesIO
from model import SimpleCNN

app = Flask(__name__)

# Load the trained model
model = SimpleCNN()
model.load_state_dict(torch.load('model.pth', map_location='cpu'))
model.eval()

# Define the transform for input images
transform = transforms.Compose([
    transforms.Resize((180, 180)),
    transforms.ToTensor(),
])

label_dict = {0: 'LAPTOP', 1: 'MOBILE_PHONE'}


def download_image(url):
    response = requests.get(url)
    response.raise_for_status()  # Ensure the request was successful
    return Image.open(BytesIO(response.content))


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
    app.run(host="0.0.0.0", port=5000)
