from concurrent.futures import process
from turtle import home
import cv2 as cv
from cv2 import Algorithm
import matplotlib.pyplot as plt
import os
import tqdm
from numpy import average
import pickle


def calculate_average_distance(matches):
    average_distance = 0
    for m in matches:
        average_distance += m.distance
    return (average_distance/(len(matches)))


def read_image(home_dir, path):
    img = cv.imread(home_dir + path)
    img = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
    return img


def process_gallery_image(img, ds1, dictionary, bef, sift):
    img2 = read_image(home_dir, "gallery_4186/" + img)
    kp2, ds2 = sift.detectAndCompute(img2, None)
    bf = bef
    matches = bf.match(ds1, ds2)
    average = calculate_average_distance(matches)
    dictionary[img] = average
    return dictionary


def write_to_file(file, dictionary):
    file.write("Query number: " + query_img + "\n")
    for key, value in dictionary.items():
        file.write(key + " ")
    file.write("\n" + "\n" + "\n" + "\n" + "\n")


if __name__ == "__main__":
    home_dir = "c:/Users/User/Documents/Cityu files/Year 3/Sem B/CS4186 Computer Vision and Image Processing/Assignments/Assignment1/"
    sift = cv.SIFT_create()
    bef = cv.BFMatcher()
    file = open("method1/results.txt", "w")
    for query_img in tqdm.tqdm(os.listdir(home_dir + "cropped_query")):
        img1 = read_image(home_dir, "cropped_query/" + query_img)
        kp1, ds1 = sift.detectAndCompute(img1, None)
        dictionary = {}
        for img in tqdm.tqdm(os.listdir(home_dir + "gallery_4186")):
            dictionary = process_gallery_image(img, ds1, dictionary, bef, sift)
        dictionary = dict(sorted(dictionary.items(), key=lambda item: item[1], reverse=False))
        outfile = open('M1dictionaries.pickle', "wb")
        pickle.dump(dictionary, outfile)
        write_to_file(file, dictionary)
    file.close()
    outfile.close()

# 86 gallery - 35 query