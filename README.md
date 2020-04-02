This program estimate the level of difficulty of the text.
We pass the path to the file through command line argumen.

Difficulty is calculated by the following formulas:
1)Automated readability index (https://en.wikipedia.org/wiki/Automated_readability_index)
2)Flesch–Kincaid readability tests (https://en.wikipedia.org/wiki/Flesch%E2%80%93Kincaid_readability_tests)
3)SMOG index (https://en.wikipedia.org/wiki/SMOG)
4)Coleman–Liau index (http://en.wikipedia.org/wiki/Coleman%E2%80%93Liau_index)

The outpul look like this:
java Main in.txt -> pass a file

Words: 1034
Sentences: 140
Characters: 6874
Syllables: 2111
Polysyllables: 177
Enter the score you want to calculate (ARI, FK, SMOG, CL, all): all
 
Automated Readability Index: 7.08 (about 13 year olds).
Flesch–Kincaid readability tests: 6.31 (about 12 year olds).
Simple Measure of Gobbledygook: 9.42 (about 15 year olds).
Coleman–Liau index: 10.66 (about 17 year olds).
