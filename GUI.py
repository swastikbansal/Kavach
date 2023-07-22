from kivy.app import App
from kivy.properties import StringProperty
from kivy.uix.screenmanager import Screen, ScreenManager

import Contacts

if __name__ == "__main__":
    contacts = Contacts.Contacts()

    #Global Variables
    # globalName = StringProperty("")
    # globalNumber = StringProperty("")
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
        self.addName = Widget.text 
    
    def numberTextValidate(self,Widget):
        self.addNumber = Widget.text 
    
    def writeContact(self,nameInput,numberInput):
        #Reading Input from both the text Box 
        self.addName = nameInput.text 
        self.addNumber = numberInput.text

        contacts.writeFile(self.addName, self.addNumber)


class ViewEmergencyContacts(Screen):
    veiwName = StringProperty("") 
    veiwNumber = StringProperty("") 

    def on_enter(self, *args):
        global name_list, number_list 

        tempName = ""
        tempNumber = ""

        # Loop for iterating both the lists at the same time and concatenating them
        for i, j in zip(name_list, number_list):
            if self.veiwName != "" and self.veiwNumber != "":
                tempName += "\n" + i
                tempNumber += "\n" + j
            else:
                self.veiwName = i
                self.veiwNumber = j

        self.veiwName += tempName    
        self.veiwNumber += tempNumber
        





class DeleteEmergencyContacts(Screen):
    pass


class ScreenManager(ScreenManager):
    pass


class KavachApp(App):
    pass


KavachApp().run()
