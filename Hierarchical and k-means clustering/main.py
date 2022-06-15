from cv2 import dft
import sklearn
from sklearn.datasets import load_wine
import numpy as np
import matplotlib.pyplot as plt
from scipy.cluster.hierarchy import dendrogram, linkage, fcluster
from sklearn.cluster import KMeans
import pandas as pd

if __name__ == "__main__":
    df = load_wine()
    # df = pd.DataFrame(df.data, columns=df.feature_names)
    # print(df)
    # df = df[["proline", "alcohol"]]
    # print(df)
    # linked = linkage(df.data, "average")
    # plt.figure(figsize=(25, 10))
    # dendrogram(linked)
    # plt.show()
    z = linkage(df.data, method="single")
    # plt.figure(figsize=(25, 10))
    kclusters = fcluster(z, 3, criterion='maxclust')
    # plt.show()
    # print(kclusters)
    cluster_size = 3
    test = fcluster(z, cluster_size, criterion='maxclust')
    summ={}
    for x in range(0,cluster_size+1):
        summ[x]= 0
    for x in test:
        summ[x]+=1
    for x in range(0,cluster_size+1):
        print(str(x)+" "+str(summ[x]))