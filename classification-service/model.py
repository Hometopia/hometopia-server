import torch.nn as nn


class SimpleCNN(nn.Module):
    def __init__(self, num_classes=9):
        super(SimpleCNN, self).__init__()

        # image size is --> (3,180,180)

        # convolutional layer with 32 filter ,input dimension is 3 because image has 3 channels
        self.conv1 = nn.Conv2d(3, 32, kernel_size=3, padding=1)
        # activation function , it adds   introduces non-linearity to the model , thus  it helps to model to learn
        # complex functions .
        self.act1 = nn.ReLU()
        # it reduces pixel number  (90,90)
        self.pool1 = nn.MaxPool2d(2)

        self.conv2 = nn.Conv2d(32, 64, kernel_size=3, padding=1)
        self.act2 = nn.ReLU()
        self.pool2 = nn.MaxPool2d(2)  # --> (45,45)

        self.conv3 = nn.Conv2d(64, 128, kernel_size=3, padding=1)
        self.act3 = nn.ReLU()
        self.pool3 = nn.MaxPool2d(2)  # --> (22,22)

        # first flatten the channels and then feed them into the fully connected layer. Given the input shape of (128, 22, 22), flattening it results in 128 * 22 * 22.
        self.fc1 = nn.Linear(128 * 22 * 22, 256)
        self.act4 = nn.ReLU()

        # dropout drops randomly neurons , here %20 of neurons are dropped randomly . It helps to prevent overfitting
        self.dropout = nn.Dropout(p=0.2)

        # The nn.Linear layer with input size 256 and output size = num_classes represents the output layer of our neural network.
        # Since we have 9 classes, the output of this layer will be passed through a softmax activation function.
        # (error function  internally applies softmax activation ,you dont need to add it to here)
        # This converts the raw outputs into probabilities, representing the likelihood of each class.
        # These probabilities are then used to calculate the error during trainin
        self.fc2 = nn.Linear(256, num_classes)

    def forward(self, x):
        # add outputs on top of each layer and return out in the end
        out = self.pool1(self.act1(self.conv1(x)))
        out = self.pool2(self.act2(self.conv2(out)))
        out = self.pool3(self.act3(self.conv3(out)))

        out = out.view(out.size(0), -1)

        out = self.act4(self.fc1(out))
        out = self.dropout(out)
        out = self.fc2(out)

        return out
