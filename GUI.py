#Kivy Libraries
from kivy.app import App
from kivy.properties import StringProperty
from kivy.uix.screenmanager import Screen, ScreenManager

#Local file imports
import Contacts


# ---------------- GUI ---------------------
class MainWin(Screen):
    pass


#Emergency Window Menu Class 
class EmergencyContacts(Screen):

    #This function is executes on press of Veiw button to extract data from Contacts 
    def veiwButton(self):
        global name_list,number_list

        data = contacts.readFile()

        for row in data :
            if row != []:
                name_list.append(row[0])
                number_list.append(row[1])


#Add Contacts Class for GUI
class AddEmergencyContacts(Screen):

    #Functions for Updating Data on Text Validation
    def nameTextValidate(self,Widget):
        self.addName = Widget.text 
    
    def numberTextValidate(self,Widget):
        self.addNumber = Widget.text 
    
    #Updating data on pressing of Button
    def addContactButton(self,nameInput,numberInput):
        #Reading Input from both the text Box 
        self.addName = nameInput.text 
        self.addNumber = numberInput.text

        contacts.writeFile(self.addName, self.addNumber)


#Veiw Contacts Screen Class and integration with backend
class ViewEmergencyContacts(Screen):
    veiwName = StringProperty("") 
    veiwNumber = StringProperty("") 
    
    #Function executes as soon as Screen opens
    def on_enter(self, *args):
        global name_list, number_list 

        #Temp var for name and number as kivy StringProperty dosen't support concatenation
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

        #Assigning final values to String Property
        self.veiwName += tempName    
        self.veiwNumber += tempNumber
    

class DeleteEmergencyContacts(Screen):
    pass

#Screen Manager class for managing multiple Classes
class ScreenManager(ScreenManager):
    pass


class KavachApp(App):
    pass


#__main__
if __name__ == "__main__":
    contacts = Contacts.Contacts()

    #Global Variables
    # globalName = StringProperty("")
    # globalNumber = StringProperty("")
    name_list = []
    number_list = []

    KavachApp().run()