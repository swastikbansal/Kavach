from kivy.app import App
from kivy.uix.screenmanager import Screen,ScreenManager

import Contacts

contacts = Contacts.Contacts()

#---------------- GUI ---------------------
class MainWin(Screen):
    pass

class EmergencyContacts(Screen):
    pass

class AddEmergencyContacts(Screen):

    def nameTextValidate(self,Widget):
        self.name = Widget.text 
    
    def numberTextValidate(self,Widget):
        self.number = Widget.text 
    
    def writeCSVFile(self,nameInput,numberInput):
        #Reading Input from both the text Box 
        self.name = nameInput.text 
        self.number = numberInput.text

        contacts.writeFile(self.name,self.number)

class VeiwEmergencyContacts(Screen):
    pass

class DeleteEmergencyContacts(Screen):
    pass

class ScreenManager(ScreenManager):
    pass


class KavachApp(App):
    pass


KavachApp().run()


