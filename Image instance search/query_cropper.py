import cv2 as cv
import numpy as np
import os

def crop_query(query_path, txt_path, save_path):
    query_img = cv.imread(query_path)
    query_img = query_img[:, :, ::-1]
    txt = np.loadtxt(txt_path)
    crop = query_img[int(txt[1]) : int(txt[1] + txt[3]), int(txt[0]) : int(txt[0] + txt[2]), :]
    cv.imwrite(save_path, crop[:, :, :: -1])
    return True


def crop_all_queries():
    for img in os.listdir("./query_4186"):
        for txt in os.listdir("./query_txt_4186"):
            if img.split(".")[0] == txt.split(".")[0]:
                query_path = "./query_4186/" + img
                txt_path = "./query_txt_4186/" + txt
                save_path = "./cropped_query/" + img
                crop_query(query_path, txt_path, save_path)


if __name__ == "__main__":
    crop_all_queries()