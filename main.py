from kivy.app import App
from kivy.metrics import dp
from kivy.properties import StringProperty
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.anchorlayout import AnchorLayout
from kivy.uix.gridlayout import GridLayout
from kivy.uix.stacklayout import StackLayout
from kivy.uix.button import Button
from kivy.uix.widget import Widget

class WidgetExample(GridLayout):
    counter = 0
    my_text = StringProperty(str(counter))
    def on_button_click(self):
        self.counter +=  1
        self.my_text = str(self.counter)
    
    def on_state(self,state):
        print("Toggle State : ",state.state)


class StackLayoutExample(StackLayout):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)

        self.orientation = "lr-tb"   
        for i in range(100):
            # size = dp(100 + i*10)
            size = dp(100)
            b = Button(text = str(i+1),size_hint = (None,None), size = (size,size))
            self.add_widget(b)

class AnchorLayoutExample(AnchorLayout):
    pass

class BoxLayoutExample(BoxLayout):
    pass
    #Code for Box Layout genrated using python
    """def __init__(self, **kwargs):
        super().__init__(**kwargs)
        # self.orientation = "vertical" 

        b1 = Button(text = "A")
        b2 = Button(text = "B")
        b3 = Button(text = "C")

        self.add_widget(b1)
        self.add_widget(b2)
        self.add_widget(b3)
"""
    
class MainWidget(Widget):
    pass

class KavachApp(App):
    pass

KavachApp().run()