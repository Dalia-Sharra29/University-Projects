import configparser

class configuration:
    def __init__(self):
        self._config = configparser.ConfigParser()

    def read_setting_file(self, list):
        self._config.read('settings.properties')
        for key in self._config["Settings"]:
            list.append(self._config["Settings"][key])
        return list
