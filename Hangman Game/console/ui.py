from repository.repository import SentenceException, Repository
from service.service import Service


class UI:
    def __init__(self, service):
        self._service = service

    def add_sentence_ui(self):
        sentence = input("Give sentence: ") + "\n"
        self._service.add_sentence(sentence)

    def print_menu(self):
        print(' ')
        print(' < add sentence > for adding a new sentence')
        print(' < game > for starting the game')
        print(' < exit > to close the application')

    def game(self):
        original_sentence = self._service.choose_sentence()
        sentence = self._service.hangman_style(original_sentence)
        hangman = "hangman"
        word = ""
        index = 0
        print(sentence)
        while word != hangman and original_sentence != sentence:
            letter = input("Make your guess: ")
            if letter in original_sentence and letter not in sentence:
                sentence = self._service.reveal_letter(letter, original_sentence, sentence)
                print(sentence)
            else:
                word = word + hangman[index]
                index += 1
                print("Stage:<", word, ">")
        if word == hangman:
            print("You lost:(")
        else:
            print("You won:D")

    def start(self):
        done = False
        while not done:
            self.print_menu()
            command = input('Give command: ')
            print(' ')
            if command == 'add sentence':
                try:
                    self.add_sentence_ui()
                except SentenceException as se:
                    print(str(se))
            elif command == 'game':
                self.game()
            elif command == 'exit':
                done = True
                print('Bye bye!')
            else:
                print('Bad command!')


repository = Repository("sentences.txt")
service = Service(repository)
ui = UI(service)
ui.start()
