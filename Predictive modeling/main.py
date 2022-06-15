from sklearn.ensemble import RandomForestClassifier
from sklearn.naive_bayes import GaussianNB
import pandas as pd
import matplotlib.pyplot as plt
from sklearn import tree

    
def plot_tree(name, classifier,  columns, classes):
    fig = plt.figure(figsize=(30, 30))
    _ = tree.plot_tree(classifier, feature_names=columns, class_names=classes, filled=True)
    fig.savefig(name)


def train_data(training_data):
    train_Y = training_data["class"]
    train_X = training_data.drop(["class"], axis=1)
    clf = RandomForestClassifier(n_estimators=7, random_state=8)
    clf.fit(train_X, train_Y)
    return clf


def test_data(testing_data, clf):
    test_X = testing_data.drop(["class"], axis=1)
    predicted_Y = clf.predict(test_X)
    return predicted_Y


def calculate_accuracy(predicted_Y, correct_Y):
    total_count = 0
    match_count = 0
    for i in range(len(predicted_Y)):
        if predicted_Y[i] == correct_Y[i]:
            match_count += 1
        total_count += 1
    return match_count * 100 / total_count


if __name__ == "__main__":
    classes = ['d ', 'h ', 'o ', 's ']
    training_data = pd.read_csv(r"ForestTypes/training.csv")
    testing_data = pd.read_csv(r"ForestTypes/testing.csv")
    correct_Y = list(testing_data["class"])
    nb = GaussianNB()
    train_Y = training_data["class"]
    train_X = training_data.drop(["class"], axis=1)
    test_X = testing_data.drop(["class"], axis=1)
    nb.fit(train_X, train_Y)
    predicted_Y = nb.predict(test_X)
    print(calculate_accuracy(predicted_Y, correct_Y))

    # clf = train_data(training_data)
    # dict = {}
    # for i in range(27):
    #     dict[training_data.columns[i + 1]] = 0
    # for i in range(7):
    #     model = clf.estimators_[i]
    #     for j in range(len(model.feature_importances_)):
    #         dict[training_data.columns[j+1]] += clf.feature_importances_[j]
    # #predicted_Y = test_data(testing_data, clf)
    #
    # dict = sorted(dict.items(), key=operator.itemgetter(1), reverse=True)
    # for i in dict:
    #     print(i)
    #plot_tree("tree7.png", clf, list(training_data.columns)[1:], classes)
    # answer = calculate_accuracy(predicted_Y, correct_Y)
    # print(answer)
    #print(answer_1)