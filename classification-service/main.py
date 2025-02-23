import matplotlib.pyplot as plt
import torch
import random
from torch import nn
from torch.utils.data import DataLoader
from torchvision import transforms, datasets
from torchvision.transforms.functional import to_pil_image

from model import SimpleCNN

# matplotlib.use('TkAgg')

train_transform = transforms.Compose([
    # Resize Image
    transforms.Resize(size=(180, 180)),
    # Turn the image into a torch.Tensor , it transforms the image to a tensor with range [0,1]. I t implies some kind of normalization
    transforms.ToTensor()
])

validation_transform = transforms.Compose([
    transforms.Resize(size=(180, 180)),
    transforms.ToTensor()
])

test_transform = transforms.Compose([
    transforms.Resize(size=(180, 180)),
    transforms.ToTensor()
])

train_dir = "data/train"  # path to the train folder
validation_dir = "data/validation"  # path to the validation folder
test_dir = "data/test"

train_data = datasets.ImageFolder(root=train_dir,
                                  transform=train_transform)

validation_data = datasets.ImageFolder(root=validation_dir,
                                       transform=validation_transform)

test_data = datasets.ImageFolder(root=test_dir,
                                 transform=test_transform)

train_set = DataLoader(dataset=train_data,
                       batch_size=32,
                       num_workers=8,
                       shuffle=True)

validation_set = DataLoader(dataset=validation_data,
                            batch_size=32,
                            num_workers=8,
                            shuffle=False)

test_set = DataLoader(dataset=test_data,
                      batch_size=32,
                      num_workers=8,
                      shuffle=False)

# I create train_data above , and I will use it here
label_dict = {y: x for x, y in train_data.class_to_idx.items()}


# Define a function to display images
def show_images(images, labels):
    plt.figure(figsize=(12, 8))
    for i in range(len(images)):
        plt.subplot(4, 4, i + 1)
        image = to_pil_image(images[i])  # Convert tensor to PIL Image
        plt.imshow(image)
        plt.title(label_dict[labels[i].item()])  # Convert numerical label to string label
        plt.axis('off')
    plt.show()


# I am going to add accuracies to these lists and I will use them outside of this function
train_accuracies = []
validation_accuracies = []


# Function for training
def train(dataloader, model, loss_fn, optimizer):
    size = len(dataloader.dataset)  # total number of images inside of loader
    num_batches = len(dataloader)  # number of batches

    model.train()

    train_loss, correct = 0, 0

    for batch, (X, y) in enumerate(dataloader):
        # move X and y to GPU for faster training
        X, y = X.to(device), y.to(device)

        # make prediction
        pred = model(X)
        # calculate loss
        loss = loss_fn(pred, y)

        # Backpropagation
        loss.backward()  # compute parameters gradients
        optimizer.step()  # update parameters
        optimizer.zero_grad()  # reset the gradients of all parameters

        # Update training loss
        train_loss += loss.item()  # item() method extracts the loss’s value as a Python float

        # Calculate training accuracy
        correct += (pred.argmax(1) == y).type(torch.float).sum().item()

    # loss and accuracy
    train_loss = train_loss / num_batches
    accuracy = 100 * correct / size

    # use this accuracy list for plotting accuracy with matplotlib
    train_accuracies.append(accuracy)

    # Print training accuracy and loss at the end of epoch
    print(f" Training Accuracy: {accuracy:.2f}%, Training Loss: {train_loss:.4f}")


# function for validation
def validation(dataloader, model, loss_fn):
    size = len(dataloader.dataset)  # total number of images inside of loader
    num_batches = len(dataloader)  # number of batches

    validation_loss, correct = 0, 0

    # sets the PyTorch model to evaluation mode, it will disable dropout layer
    model.eval()

    with torch.no_grad():  # disable gradient calculation
        for X, y in dataloader:
            # move X and y to GPU for faster training
            X, y = X.to(device), y.to(device)
            pred = model(X)  # make prediction
            validation_loss += loss_fn(pred, y).item()

            # if prediction is correct add 1 to correct variable.
            correct += (pred.argmax(1) == y).type(torch.float).sum().item()

    # loss and accuracy
    validation_loss /= num_batches
    accuracy = 100 * correct / size

    validation_accuracies.append(accuracy)

    # Print test accuracy and loss at the end of epoch
    print(f" Validation Accuracy: {accuracy:.2f}%, Validation Loss: {validation_loss:.4f}")


def test(test_loader, model, loss_fn):
    model.eval()
    size = len(test_loader.dataset)
    num_batches = len(test_loader)

    test_loss, correct = 0, 0

    with torch.no_grad():
        for X, y in test_loader:
            X, y = X.to(device), y.to(device)
            pred = model(X)
            test_loss += loss_fn(pred, y).item()
            correct += (pred.argmax(1) == y).type(torch.float).sum().item()

            num_images = len(X)

            rows = (num_images + 4) // 5
            fig, axs = plt.subplots(rows, 5, figsize=(15, rows * 3))

            axs = axs.flatten()
            for i in range(num_images):
                image = to_pil_image(X[i].cpu())
                true_label = label_dict[y[i].item()]
                pred_label = label_dict[pred.argmax(1)[i].item()]

                axs[i].imshow(image)
                axs[i].axis('off')

                axs[i].set_title(f"True: {true_label}\nPred: {pred_label}", fontsize=10)

            for j in range(num_images, len(axs)):
                axs[j].axis('off')

            plt.tight_layout()
            # plt.show()
            plt.savefig('result/result-{}.png'.format(random.randint(1, 1000)))
            plt.clf()

            # for i in range(len(X)):
            #     if i == 10:
            #         break
            #     image = to_pil_image(X[i].cpu())
            #     true_label = label_dict[y[i].item()]
            #     pred_label = label_dict[pred.argmax(1)[i].item()]
            #
            #     plt.imshow(image)
            #     plt.title(f"True: {true_label}\nPred: {pred_label}")
            #     plt.axis('off')
            #     plt.show()

    test_loss /= num_batches
    accuracy = 100 * correct / size

    print(f"Test Accuracy: {accuracy:.2f}%, Test Loss: {test_loss:.4f}")


def visualize(train_accuracies, validation_accuracies):
    epoch_number = len(train_accuracies)

    plt.plot(range(1, epoch_number + 1), train_accuracies, 'r', label='Training accuracy')
    plt.plot(range(1, epoch_number + 1), validation_accuracies, 'b', label='Validation accuracy')
    plt.legend()
    plt.xlabel("Epoch Number")
    plt.ylabel("Accuracies")
    # plt.show()
    plt.savefig('result/accuracies.png')
    plt.clf()


if __name__ == "__main__":
    # if GPU is available , use it while training
    device = "cuda" if torch.cuda.is_available() else "cpu"
    model = SimpleCNN(num_classes=6)
    model.to(device)

    # Loss funciton and optimizer
    loss_fn = nn.CrossEntropyLoss()
    optimizer = torch.optim.SGD(model.parameters(), lr=1e-3)

    # epoch number
    epochs = 30

    # loop for training model
    for t in range(epochs):
        print(f"Epoch {t + 1}")
        train(train_set, model, loss_fn, optimizer)
        validation(validation_set, model, loss_fn)
        print("----------------------------")
    print("Done!")

    test(test_set, model, loss_fn)

    torch.save(model.state_dict(), 'model.pth')

    visualize(train_accuracies, validation_accuracies)
