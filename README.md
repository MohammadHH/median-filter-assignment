# Median Filter

> A java app built from ground up to produce the median filter of a given file

This project aims at computing the median filter of a given file. Given an input file, an output destination and a
windows size, the code shall parse the file, process its entries, produce the median filter and save that to the given
destination.

Currently, the project is implemented to take only text files. However, it is structured such that it can be easily
extended to account for other types of files.

A file is seen as an array of channels, each channel is a 2D array.A text file is an array of one channel, and an RGB
image is an array of 3 channels. Since the dimensions of files is known before storing it in the 3D array, I based my
implementation on regular arrays, not on array lists.

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

For text files, the app first validates the input file and returns its dimensions (i.e. number of lines and elements per
each line). After validating the file, its content is read, hence a regular 2D array is used since the size is known

## Compatibility

The code was written using java 11 in mind, it can be compiled with java versions from 8 to 10 without any
modifications. For versions older than 8, one should consider replacing lambdas with anonymous inner classes. Also, for
versions less than 7, try-with-resources should be replaced with regular try and closing resources should be done
manually in the finally block.

## App General Structure & Flow

![Class Diagram](https://i.ibb.co/r29hmkD/class-diagram.png)

The `Main` class is the entry point of the program execution. In it,passed arguments are validated
using `ArgumentsValidator` which has a method that checks proper length of arguments, parsable window size and throws
errors as necessary.

After validating the arguments, configuration based on them get extracted using `Configuration` class which stores
input,output files path, window size, input file type and assign number of threads to be used in multithreading
sections. By default, number of threads will be the number of available processor in the system where the code runs.
Input type is set based on input file MIME, when it contains image the type is an image and when it is a text or null
its considered a text, otherwise an exception is thrown.

The input file is read based on its type. Text file is validated using `TextFileValidator` which takes a file, tries to
parse it and returns its dimensions. A valid text file is a file that has well-formed integer values between 0 and 255,
its entries are separated by spaces and/or tabs and its rows is separated by new line, number of entries per each line
should be the same. A file can be empty and can have empty lines and still be valid. Image type input will throw
exception since its read method is not implemented yet.

After validating the text file, its content is read using `ReadFile`'s readTextFile method which takes the text file and
its dimensions and store its content into 3D array (a single channel 2D array).

The text file channel is passed to `ChannelsMedianFilter` which has a method to calculate median of files in each
channel. This file has default `QuickMedianSelection` as its median finder implementation
and `ShrinkingWindowBoundaries` as its boundaries implementation. The result from applying the median over these
channels is another 3D array representing median for each entry in each channel.

The resulted median channels are stored to an ouput file using `WriteFile` which now supports text files only.

## Usage

`java -jar assignment.jar inputFile outputFile windowSize`

* inputFile: destination of a valid and well-formed text file
* outputFile: destination of the file after applying median filter on it
* windowSize: the maximum size of the window.

## Supporting RGB Images

To support RGB images (3 channels), add appropriate read and write methods in `WriteFile` and `ReadFile` classes.