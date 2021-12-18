# Splitting
How to partition money (into GBP)

Calculates and neatly presents the partitions of an amount of money in GB pennies (as to avoid floating point errors).
This is very similar to partitions in math except here you have a select few blocks as it were.
The actual calculation is not hard, it is presenting the data neatly.
With the use of recursion partitions become simple and can do a whopping 19p, yes 19p using 1.048e+9 bytes of memory :)
Recursion is expensive (memory wise)
The point was the algorithms

The reason the denominations are 5, 2, 1 is because in britain denominations follow a 5, 2, 1 patter:
£50, £20, £10, £5, £2, £1, 50p, 20p, 10p, 5p, 2p, 1p
|----notes------|  |------------coins--------------|
And as 19 is the upper limit on a more reasonable heap size just use it an estimate so just ignore the 'p' in the output

Please enjoy
