import pandas as pd
import matplotlib.pyplot as plt
from sklearn import tree
from sklearn.metrics import classification_report, accuracy_score, ConfusionMatrixDisplay


def plot_tree(name, classifier,  columns, classes):
    fig = plt.figure(figsize=(30, 30))
    _ = tree.plot_tree(classifier, feature_names=columns, class_names=classes, filled=True)
    fig.savefig(name)


def print_confusion_matrix(name: str, y_true: list, y_pred: list):
    print(f"Confusion Matrix:")
    ConfusionMatrixDisplay.from_predictions(y_true, y_pred, labels=['d ', 'h ', 'o ', 's ']).plot()
    plt.savefig(f"{name}.png")


def train_data(training_data):
    train_Y = training_data["class"]
    train_X = training_data.drop(["class"], axis=1)
    clf = tree.DecisionTreeClassifier(criterion='entropy', max_depth=2, splitter="best", min_samples_split=2,
                                      min_samples_leaf=1, min_weight_fraction_leaf=0, min_impurity_decrease=0, max_features=None,
                                      random_state=None, max_leaf_nodes=3, class_weight=None, ccp_alpha=0).fit(train_X, train_Y)
    return clf


def test_data(testing_data, clf):
    test_Y = testing_data["class"]
    test_X = testing_data.drop(["class"], axis=1)
    predicted_Y = clf.predict(test_X)
    return predicted_Y


def calculate_accuracy(predicted_Y, test_data):
    total_count = 0
    match_count = 0
    for i in range(len(predicted_Y)):
        if predicted_Y[i] == test_data["class"][i]:
            match_count += 1
        total_count += 1
    return match_count * 100 / total_count


if __name__ == "__main__":
    classes = ['d ', 'h ', 'o ', 's ']
    training_data = pd.read_csv(r"ForestTypes\training.csv")
    clf = train_data(training_data)

    testing_data = pd.read_csv(r"ForestTypes\testing.csv")
    predicted_Y = test_data(testing_data, clf)
    correct_Y = list(testing_data["class"])
    # plot_tree("decision_tree8.png", clf, list(training_data.columns)[1:], classes)
    print_confusion_matrix("matrix8", correct_Y, predicted_Y)
    print(calculate_accuracy(predicted_Y, testing_data))
