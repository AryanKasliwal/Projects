from cv2 import StereoSGBM
import numpy as np
import cv2
from matplotlib import pyplot as plt
from math import log10, sqrt
# import psnr_cal


def calculate_psnr(img1, img2):
    # img1 and img2 have range [0, 255]
    img1 = img1.astype(np.float64)
    img2 = img2.astype(np.float64)
    mse = np.mean((img1 - img2)**2)
    if mse == 0:
        return float('inf')
    return 20 * log10(255.0 / sqrt(mse))


def process_images(home_dir):
    test_imgs = ["Art", "Dolls", "Reindeer"] 
    modes = [cv2.STEREO_SGBM_MODE_HH, cv2.STEREO_SGBM_MODE_SGBM, cv2.STEREO_SGBM_MODE_SGBM_3WAY, cv2.STEREO_SGBM_MODE_HH4]
    for i in range(3):
        imgL = cv2.imread(home_dir + "StereoMatchingTestings/" + test_imgs[i] + "/view1.png",0)
        imgR = cv2.imread(home_dir + "StereoMatchingTestings/" + test_imgs[i] + "/view5.png",0)
        
        # stereo = cv2.StereoSGBM_create(blockSize=1,minDisparity=0,P1=1, P2=80, numDisparities=256, uniquenessRatio=0, disp12MaxDiff=100, speckleWindowSize = 0)
        stereo = cv2.StereoSGBM_create(
            numDisparities=118, 
            blockSize=3, 
            minDisparity=10, 
            uniquenessRatio=10,
            P1=1, 
            P2=2, 
            disp12MaxDiff=0, 
            preFilterCap=1000, 
            speckleRange=1,
            speckleWindowSize=10000,
            mode=cv2.StereoSGBM_MODE_HH)
        disparity = stereo.compute(imgL,imgR)
        plt.imshow(disparity,'gray')
        cv2.imwrite(home_dir + "PSNR_Assignment2/PSNR_Python/pred/" + test_imgs[i] + "/disp1.png", disparity)
        # plt.show()

if __name__ == "__main__":
    home_dir = "C:/Users/User/Documents/Cityu files/Year 3/Sem B/CS4186 Computer Vision and Image Processing/Assignments/Assignment2/"
    process_images(home_dir)
    exec(compile(open(home_dir + 'PSNR_Assignment2/PSNR_Python/psnr_cal.py', "rb").read(), home_dir + 'PSNR_Assignment2/PSNR_Python/psnr_cal.py', 'exec'))
