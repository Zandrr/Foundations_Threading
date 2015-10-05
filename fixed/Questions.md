# Question 1:

The ```broken``` program has a ton of issues.  Most, if not all of them, are related to race conditions inside of critical code. When you first try to run the ```broken``` code without any changes, the most apparent problem is that the queue is empty and nothing is being added to it.