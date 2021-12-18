# Splitting<br>
How to partition money (into GBP)<br>
<br>
Calculates and neatly presents the partitions of an amount of money in GB pennies (as to avoid floating point errors).<br>
This is very similar to partitions in math except here you have a select few blocks as it were.<br>
The actual calculation is not hard, it is presenting the data neatly.<br>
With the use of recursion partitions become simple and can do a whopping 19p, yes 19p using 1.048e+9 bytes of memory :)<br>
Recursion is expensive (memory wise)<br>
The point was the algorithms<br>
<br>
The reason the denominations are 5, 2, 1 is because in britain denominations follow a 5, 2, 1 patter:<br>
£50, £20, £10, £5, £2, £1, 50p, 20p, 10p, 5p, 2p, 1p<br>
|----notes------|  |------------coins--------------|<br>
And as 19 is the upper limit on a more reasonable heap size just use it an estimate so just ignore the 'p' in the output<br>
<br>
Please enjoy
