import pickle

from repository.GradeRepository import graderepo


class GradeTextRepository(graderepo):
    def __init__(self, fileName, studentrepo, disciplinerepo):
        super().__init__(studentrepo, disciplinerepo)
        self._fileName = fileName
        self._loadFile()

    def _loadFile(self):
        f = open(self._fileName, 'r')
        while True:
            s = f.readline().strip()
            if not s:
                break
            list = s.split(',')
            self.add_grade(*list)
        f.close()

    def _saveFile(self):
        f = open(self._fileName, 'w')
        try:
            for g in self.grade_list:
                grade_str =  str(g.discipline_id) + "," + str(g.student_id) + "," + str(g.grade) + "\n"
                f.write(grade_str)
            f.close()
        except Exception as e:
            print("An error occurred -" + str(e))

    def add_grade(self, discipline_id, student_id, value):
        grade = super().add_grade(discipline_id, student_id, value)
        self._saveFile()
        return grade

    def remove_grade(self, discipline_id, student_id):
        super().remove_grade(discipline_id, student_id)
        self._saveFile()

class GradeBinaryFileRepository(graderepo):
    def __init__(self, fileName, studentrepo, disciplinerepo):
        super().__init__(studentrepo, disciplinerepo)
        self._fileName = fileName
        self.grade_list = self.read_binary_file()

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
        pickle.dump(self.grade_list, f)
        f.close()

    def add_grade(self, discipline_id, student_id, value):
        grade = super().add_grade(discipline_id, student_id, value)
        self.write_binary_file()
        return grade

    def remove_grade(self, discipline_id, student_id):
        super().remove_grade(discipline_id, student_id)
        self.write_binary_file()