#Kivy Libraries
from kivy.app import App
from kivy.properties import StringProperty
from kivy.uix.label import Label
from kivy.uix.textinput import TextInput
from kivy.uix.checkbox import CheckBox
from kivy.uix.togglebutton import ToggleButton
from kivy.uix.button import Button
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.popup import Popup
from kivy.uix.screenmanager import Screen, ScreenManager

#Local file imports
import Contacts

#PopUp Box configuration Fucntion
def popupMsg(msg):
    # Create a BoxLayout to hold the error message and the button
        box_layout = BoxLayout(orientation='vertical')
        label = Label(text=f'{msg}')
        ok_button = Button(text='OK', size_hint=(1, None), height='40dp')
        box_layout.add_widget(label)
        box_layout.add_widget(ok_button)

        # Create and show the popup with the box_layout as the content
        popup = Popup(title='Invalid Input',
                        content=box_layout,
                        size_hint=(None, None), size=(400, 200))

        # Set the button's callback function to dismiss the popup when clicked
        ok_button.bind(on_release=popup.dismiss)

        popup.open()
    

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

        #If number entered is not valid
        if not self.addNumber.isdigit() or len(self.addNumber) != 10:
            popupMsg("Enter a valid number.")
        
        #If Data entered is correct
        else:
            popupMsg("Contact added Successfully.")
            contacts.writeFile(self.addName, self.addNumber)
            self.manager.current = "Contacts_Menu"
            self.manager.transition.direction = "right"

#View Contacts Screen Class and integration with backend
class ViewEmergencyContacts(Screen):
    
    def on_enter(self, *args):
        global name_list, number_list

        # Clear any existing widgets from the set_BoxLayout
        self.ids.set_BoxLayout.clear_widgets()

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
        
        #Clearing Lists so that the same data dosen't repeat
        name_list.clear()
        number_list.clear()


#Edit First Win       
class EditEmergencyContacts(Screen):
    def onActiveToggleButton(self, togglebutton, value, name, number):
        global edit_contact
        if value == "down":
            # If the toggle button is activated (selected), enable the text input boxes and update the editContact with the selected option
            self.editContact = [[name, number]]
        edit_contact = self.editContact
        

    def on_enter(self, *args):
        global name_list, number_list

        # Initialize editContact as None when no toggle button is selected
        self.editContact = None

        # Clear any existing widgets from the set_BoxLayout
        self.ids.set_BoxLayout.clear_widgets()

        # Loop through the name_list and number_list to create individual labels and toggle buttons
        for name, number in zip(name_list, number_list):
            name_label_text = f"{name}"
            number_label_text = f"{number}"
            
            set_BoxLayout = BoxLayout(size_hint_y=None, height="40dp")
            self.name_textInput = TextInput(text=name_label_text, size_hint=(.45, None), height="40dp", font_size=25, disabled=True)
            self.number_textInput = TextInput(text=number_label_text, size_hint=(.45, None), height="40dp", font_size=25, disabled=True)

            # Create toggle buttons with group name "contact_option" for mutually exclusive behavior
            togglebutton = ToggleButton(group="contact_option", size_hint=(.1, None), height="40dp")
            togglebutton.bind(state=lambda togglebutton, value, name=name, number=number: self.onActiveToggleButton(togglebutton, value, name, number))

            set_BoxLayout.add_widget(togglebutton)
            set_BoxLayout.add_widget(self.name_textInput)
            set_BoxLayout.add_widget(self.number_textInput)
            
            self.ids.set_BoxLayout.add_widget(set_BoxLayout)
        
        # Clearing Lists so that the same data doesn't repeat
        name_list.clear()
        number_list.clear()
    
    def updateContacts(self):
       if self.editContact:
           self.manager.current = "Edit_Update_Contacts"  # Change this to the appropriate name for the AddUpdateEmergencyContacts screen
           self.manager.transition.direction = "right"
           
class UpdateContactsData(Screen):
    global edit_contact
    #Updating data on pressing of Button
    def updateContactButton(self,nameInput,numberInput):
        #Reading Input from both the text Box 
        self.addName = nameInput.text 
        self.addNumber = numberInput.text 

        #If number entered is not valid
        if not self.addNumber.isdigit() or len(self.addNumber) != 10:
            popupMsg("Enter a valid number.")
        
        #If Data entered is correct
        else:
            updateData = [[self.addName,self.addNumber]] 
            contacts.updateFile(edit_contact, updateData)
            popupMsg("Contact updated Successfully.")
            self.root.current = "Edit_Update_Contacts"
            self.manager.transition.direction = "right"
            
            

class DeleteEmergencyContacts(Screen):
    def onActiveCheckbox(self, checkbox, value, name, number):
        if value:
            # If the checkbox is checked, add the name and number to the deleteList
            self.deleteList.append([name, number])
        else:
            # If the checkbox is unchecked, remove the name and number from the deleteList
            self.deleteList.remove([name, number])

    def on_enter(self, *args):
        global name_list, number_list

        # Initialize deleteList as an empty list
        self.deleteList = []

        # Clear any existing widgets from the set_BoxLayout
        self.ids.set_BoxLayout.clear_widgets()

        # Loop through the name_list and number_list to create individual labels
        for name, number in zip(name_list, number_list):
            name_label_text = f"{name}"
            number_label_text = f"{number}"
            
            set_BoxLayout = BoxLayout(size_hint_y=None, height="40dp")
            name_label = Label(text=name_label_text, size_hint=(.45, None), height="40dp", font_size=25)
            number_label = Label(text=number_label_text, size_hint=(.45, None), height="40dp", font_size=25)

            checkbox = CheckBox(size_hint=(.1, None), height="40dp")
            checkbox.bind(active=lambda checkbox, value, name=name, number=number: self.onActiveCheckbox(checkbox, value, name, number))

            set_BoxLayout.add_widget(checkbox)
            set_BoxLayout.add_widget(name_label)
            set_BoxLayout.add_widget(number_label)
            
            self.ids.set_BoxLayout.add_widget(set_BoxLayout)
        
        #Clearing Lists so that the same data dosen't repeat
        name_list.clear()
        number_list.clear()

    
    def deleteContacts(self):
        contacts.deleteFile(self.deleteList)
        popupMsg("Contact Deleted Successfully.")
        self.manager.current = "Contacts_Menu"
        self.manager.transition.direction = "right"
        

    
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
    edit_contact = []

    KavachApp().run()