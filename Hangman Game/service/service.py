class Service:
    def __init__(self, repository):
        self._repository = repository

    def add_sentence(self, sentence):
        self._repository.add_sentence(sentence)

    def letter_list(self, words_list):
        letters = []
        for i in range(len(words_list)):
            word = words_list[i]
            letters.append(word[0])
            letters.append(word[len(word) - 1])
        return letters

    def choose_sentence(self):
        sentence = self._repository.choose_sentence()
        return sentence

    def hangman_style(self, sentence):
        printed_sentence = ""
        words = sentence.split(' ')
        letters = self.letter_list(words)
        last_word = words[len(words) - 1].split("\n")
        words[len(words) - 1] = last_word[0]

        for i in range(len(words)):
            word = words[i]
            length = len(word)
            printed_sentence = printed_sentence + word[0]
            for k in range(1, length - 1):
                if word[k] not in letters:
                    printed_sentence = printed_sentence + "_"
                else:
                    printed_sentence = printed_sentence + word[k]
            printed_sentence = printed_sentence + word[length - 1] + " "

        printed_sentence = printed_sentence + "\n"
        return printed_sentence

    def reveal_letter(self, letter, original_sentence, current_sentence):
        printed_sentence = ""
        for i in range(len(original_sentence) - 1):
            if original_sentence[i] != letter:
                printed_sentence = printed_sentence + current_sentence[i]
            else:
                printed_sentence = printed_sentence + original_sentence[i]
        printed_sentence = printed_sentence + "\n"
        return printed_sentence
