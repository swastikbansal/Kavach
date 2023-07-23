#Kivy Libraries
from kivy.app import App
from kivy.properties import StringProperty
from kivy.uix.label import Label
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.screenmanager import Screen, ScreenManager

#Local file imports
import Contacts

#-------------- Functions -----------------

def viewContacts():
    global name_list, number_list 

    viewName = ""
    viewNumber = ""

    #Temp var for name and number as kivy StringProperty dosen't support concatenation
    tempName = ""
    tempNumber = ""

    # Loop for iterating both the lists at the same time and concatenating them
    for i, j in zip(name_list, number_list):
        if viewName != "" and viewNumber != "":
            tempName += "\n" + i
            tempNumber += "\n" + j

        else:
            viewName = i
            viewNumber = j

    #Assigning final values to String Property
    viewName += tempName    
    viewNumber += tempNumber



    return viewName,viewNumber
    

# ---------------- GUI ---------------------
class MainWin(Screen):
    pass


#Emergency Window Menu Class 
class EmergencyContacts(Screen):

    #This function is executes on press of view button to extract data from Contacts 
    def view(self):
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


#View Contacts Screen Class and integration with backend
class ViewEmergencyContacts(Screen):
    
    def on_enter(self, *args):
        # Function to create and update the labels=
        global name_list, number_list

        # Loop through the name_list and number_list to create individual labels
        for name, number in zip(name_list, number_list):
            name_label_text = f"{name}"
            number_label_text = f"{number}"
            
            set_BoxLayout = BoxLayout(size_hint_y=None, height="40dp")
            name_label = Label(text=name_label_text, size_hint=(.5, None), height="40dp",font_size = 25)
            number_label = Label(text=number_label_text, size_hint=(.5, None), height="40dp",font_size = 25)
            
            set_BoxLayout.add_widget(name_label)
            set_BoxLayout.add_widget(number_label)
            self.ids.set_BoxLayout.add_widget(set_BoxLayout)

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
    name_list = []
    number_list = []

    KavachApp().run()