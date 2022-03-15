import random

class SentenceException(Exception):
    '''
    Exception class for sentences
    '''
    def __init__(self, msg):
        self._msg = msg


class Repository:
    def __init__(self, filename):
        self._list = []

        self._filename = filename
        self._loadFile()

    @property
    def list(self):
        return self._list

    def _loadFile(self):
        f = open(self._filename, 'r')
        while True:
            sentence = f.readline()
            if not sentence:
                break
            self.add_sentence(sentence)
        f.close()

    def _saveFile(self):
        f = open(self._filename, 'w')
        for sentence in self.list:
            f.write(sentence)
        f.close()

    def add_sentence(self, sentence):
        for s in self.list:
            if str(s) == str(sentence):
                raise SentenceException('The sentence already exist in the file!')
        if self.validate_sentence(sentence):
            self.list.append(sentence)
        self._saveFile()

    def validate_sentence(self, sentence):
        if len(str(sentence)) == 0:
            raise SentenceException("Sentence can't be empty!")
        list = sentence.split(' ')
        for i in range(len(list)):
            word = list[i]
            if len(str(word)) < 3:
                raise SentenceException("Sentence can't contain words with less than 3 letters!")
        return True

    def choose_sentence(self):
        sentence = random.choice(self.list)

        return sentence
