# Median Filter

> A java app built from ground up to produce the median filter of a given file

This project aims at computing the median filter of a given file. Given an input file, an output destination and a
windows size, the code shall parse the file, process its entries, produce the median filter and save that to the given
destination.

Currently, the project is implemented to take only text files. However, it is structured such that it can be easily
extended to account for other types of files.

## Performance & Efficiency Consideration

The speed of execution of the program depends heavily on the algorithm being used in median calculation, hence my
implementation considered a fast algorithm,the quick select, to calculate the median of a given array. This algorithm
performs median selection in linear time most of the time, though its performance can decline to quadratic order in
worst case.

Since the median is calculated for each element in the channel, and taking into account that each channel is a group of
rows, the calculation of the median in each row can be considered a single task and thus my implementation assigned
these tasks to a group of threads which accomplish them concurrently.

The multithreading implementation comes free of data races. Calculating median for each entry requires a read access of
that entry and its neighboring entries. Although such access might be done at the same time by different threads, still
this wouldn't create any data race since it is only a read operation.

To manage threads, I used java executors service which provides a pool of threads and allows for assigning tasks to it.

To avoid memory leaks, the try-with-resources is used with any closable resource, such as scanners and writers, this
technique guarantees an implicit proper closing of a resource after finishing using it.

## Compatibility

The code was written using java 11 in mind, it could be compiled with java versions from 8 to 10 without any
modifications. For versions older than 8, one should consider replacing lambdas with anonymous inner classes. Also for
versions less than 7, try-with-resources should be replaced with regular try and closing resources should be done
manually in the finally block.