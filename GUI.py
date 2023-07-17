from kivy.app import App
from kivy.properties import StringProperty
from kivy.uix.screenmanager import Screen, ScreenManager

import Contacts

if __name__ == "__main__":
    contacts = Contacts.Contacts()

    #Global Variables
    name = StringProperty("")
    number = StringProperty("")
    name_list = []
    number_list = []

# ---------------- GUI ---------------------
class MainWin(Screen):
    pass


class EmergencyContacts(Screen):
    def veiwButton(self):
        global name_list,number_list

        data = contacts.readFile()

        for row in data :
            if row != []:
                name_list.append(row[0])
                number_list.append(row[1])


class AddEmergencyContacts(Screen):

    def nameTextValidate(self,Widget):
        self.name = Widget.text 
    
    def numberTextValidate(self,Widget):
        self.number = Widget.text 
    
    def writeContact(self,nameInput,numberInput):
        #Reading Input from both the text Box 
        self.name = nameInput.text 
        self.number = numberInput.text

        contacts.writeFile(self.name, self.number)


class ViewEmergencyContacts(Screen):
    global name_list,number_list 
    global name,number

    for i,j in zip(name_list,number_list):
        name = i
        number = j




class DeleteEmergencyContacts(Screen):
    pass


class ScreenManager(ScreenManager):
    pass


class KavachApp(App):
    pass


KavachApp().run()
