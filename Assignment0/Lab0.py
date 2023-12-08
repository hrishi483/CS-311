import numpy as np
import random
import time

class infiltrator:
    def __init__(self,width,length):
        self.width = width
        self.length = length
        border=np.zeros((width,length))
        self.border=border


    def conditions(self,position):
        #If he is at the corners
        next_step=[]
        if self.border[0][0] ==position :
            next_step.append(self.border[0][1])
            next_step.append(self.border[1][0])
            next_step.append(self.border[1][1])
        
        elif  self.border[self.width-1][0]==position:          
            next_step.append(self.border[self.width-2][0])
            next_step.append(self.border[self.width-1][1])
            next_step.append(self.border[self.width-2][1])

        elif self.border[0][self.length-1]==position:
            next_step.append(self.border[0][self.length-2])
            next_step.append(self.border[1][self.length-1])
            next_step.append(self.border[1][self.length-2])

        elif self.border[self.width-1][self.length-1]==position:
            next_step.append(self.border[self.width-1][self.length-2])
            next_step.append(self.border[self.width-2][self.length-1])
            next_step.append(self.border[self.width-2][self.length-2])

        #Upper Row
        elif (position[0]==0) and (0<position[1]<self.length-1):
            next_step.append(self.border[0][position[1]-1])
            next_step.append(self.border[0][position[1]+1])
            next_step.append(self.border[1][position[1]])
            next_step.append(self.border[1][position[1]-1])
            next_step.append(self.border[1][position[1]+1])

        elif (position[0]==self.width-1) and (0<position[1]<self.length-1):
            next_step.append(self.border[self.width-1][position[1]-1])
            next_step.append(self.border[self.width-1][position[1]+1])
            next_step.append(self.border[self.width-2][position[1]])
            next_step.append(self.border[self.width-2][position[1]-1])
            next_step.append(self.border[self.width-2][position[1]+1])

        elif (position[1]==0) and (0<position[1]<self.width-1):
            next_step.append(self.border[position[0]-1][0])
            next_step.append(self.border[position[0]+1][0])
            next_step.append(self.border[position[0]][1])
            next_step.append(self.border[position[0]+1][1])
            next_step.append(self.border[position[0]-1][1])

        elif (position[1]==self.length-1) and (0<position[1]<self.width-1):
            next_step.append(self.border[position[0]-1][self.length-1])
            next_step.append(self.border[position[0]+1][self.length-1])
            next_step.append(self.border[position[0]][self.length-2])
            next_step.append(self.border[position[0]+1][self.length-2])
            next_step.append(self.border[position[0]-1][self.length-2])
            
        else:
            next_step.append(self.border[position[0]-1][position[1]])
            next_step.append(self.border[position[0]][position[1]-1])
            next_step.append(self.border[position[0]+1][position[1]])
            next_step.append(self.border[position[0]][position[1]+1])
            next_step.append(self.border[position[0]-1][position[1]-1])
            next_step.append(self.border[position[0]+1][position[1]+1])
            next_step.append(self.border[position[0]-1][position[1]+1])
            next_step.append(self.border[position[0]+1][position[1]-1])

        return next_step
                             

class AttackingCountry:
    def __init__(self, country,width,length):
        self.country = country
        self.width = width
        self.start_width= random.randint(0,width)
        self.start_position=self.border[0][self.start_width]

        self.infiltrator_person = infiltrator(width,length)

    def infiltrator_move(self,position):      
        next_step_taken = np.choice(self.infiltrator_person.conditions(self.position))
        print(next_step_taken)
        return next_step_taken


class DefendingCountry:
    def __init__(self,width,length,p=0.5):
        self.width = width	
        self.length = length
        self.p=p
        border=np.zeros((width,length))
        self.border = border

    def create_border(self):
        for i in range(self.width):
            for j in range(self.length):
                self.border[i][j]=random.randint(0,1)

        for i in range(self.width):
            print("\n")
            for j in range(self.length):
                print(self.border[i][j],end=" ")

sensor_boder=DefendingCountry(3,10)
sensor_boder.create_border()


# class Game:

