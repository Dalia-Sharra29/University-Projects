import pickle

from repository.DisciplineRepository import disciplinerepo


class DisciplineTextRepository(disciplinerepo):
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
            self.add_discipline(*list)
        f.close()

    def _saveFile(self):
        f = open(self._fileName, 'w')
        try:
            for d in self.discipline_list:
                discipline_str =  str(d.id) + "," + str(d.name) + "\n"
                f.write(discipline_str)
            f.close()
        except Exception as e:
            print("An error occurred -" + str(e))

    def add_discipline(self, id, name):
        disc = super().add_discipline(id, name)
        self._saveFile()
        return disc

    def remove_discipline(self, discipline_id):
        found, obj = super().remove_discipline(discipline_id)
        self._saveFile()
        return found, obj

    def update_discipline(self, discipline_id, discipline_name):
        obj, name = super().update_discipline(discipline_id, discipline_name)
        self._saveFile()
        return obj, name

    def search_discipline(self, search_str):
        return super().search_discipline(search_str)


class DisciplineBinaryFileRepository(disciplinerepo):
    def __init__(self, fileName):
        super().__init__()
        self._fileName = fileName
        self.discipline_list = self.read_binary_file()

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
        pickle.dump(self.discipline_list, f)
        f.close()

    def add_discipline(self, discipline_id, discipline_name):
        disc = super().add_discipline(discipline_id, discipline_name)
        self.write_binary_file()
        return disc

    def remove_discipline(self, discipline_id):
        found, obj = super().remove_discipline(discipline_id)
        self.write_binary_file()
        return found, obj

    def update_discipline(self, discipline_id, discipline_name):
        obj, name = super().update_discipline(discipline_id, discipline_name)
        self.write_binary_file()
        return obj, name

    def search_discipline(self, search_str):
        return super().search_discipline(search_str)