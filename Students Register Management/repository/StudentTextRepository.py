import pickle

from repository.StudentRepository import *


class StudentTextRepository(studentrepo):
    def __init__(self, fileName):
        super().__init__()
        self._fileName = fileName
        self._loadFile()

    def _loadFile(self):
        f = open(self._fileName, 'r')
        while True:
            s = f.readline().strip()
            if not s:
                break
            list = s.split(',')
            self.add_student(*list)
        f.close()

    def _saveFile(self):
        f = open(self._fileName, 'w')
        try:
            for s in self.student_list:
                student_str =  str(s.id) + "," + str(s.name) + "\n"
                f.write(student_str)
            f.close()
        except Exception as e:
            print("An error occurred -" + str(e))

    def add_student(self, id, name):
        stud = super().add_student(id, name)
        self._saveFile()
        return stud

    def remove_student(self, id):
        found, obj = super().remove_student(id)
        self._saveFile()
        return found, obj

    def update_student(self, student_id, student_name):
        obj, name = super().update_student(student_id, student_name)
        self._saveFile()
        return obj, name

    def search_student(self, search_str):
        return super().search_student(search_str)

class StudentBinaryFileRepository(studentrepo):
    def __init__(self, fileName):
        super().__init__()
        self._fileName = fileName
        self.student_list = self.read_binary_file()

    def read_binary_file(self):
        try:
            f = open(self._fileName, "rb")
            result = pickle.load(f)
            f.close()
            return result
        except EOFError:
            """
                This is raised if input file is empty
            """
            return []
        except IOError as e:
            """
                Here we 'log' the error, and throw it to the outer layers 
            """
            print("An error occured - " + str(e))
            raise e

    def write_binary_file(self):
        f = open(self._fileName, "wb")
        pickle.dump(self.student_list, f)
        f.close()

    def add_student(self, student_id, name):
        stud = super().add_student(student_id, name)
        self.write_binary_file()
        return stud

    def remove_student(self, student_id):
        found, obj = super().remove_student(student_id)
        self.write_binary_file()
        return found, obj

    def update_student(self, student_id, student_name):
        obj, name = super().update_student(student_id, student_name)
        self.write_binary_file()
        return obj, name

    def search_student(self, search_str):
        return super().search_student(search_str)
