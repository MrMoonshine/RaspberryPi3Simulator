#include "wiringPi.h"
#include "softPwm.h"
#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>

#define BUTTON 0
#define LED 1
#define PWMLED 2

#define CLEAR 0

#define DELAYTIME 500

void waitForRisingEdge(unsigned int pin){
  while(1){
    if(digitalRead(pin) == 1){
      while(1){
        if(digitalRead(pin) == 0){
          delay(100);
          return;
        }
      }
    }

    delay(100);
  }
}

int main(void){
  wiringPiSetupGpio();
  pinMode(LED,OUTPUT);
  pinMode(BUTTON,INPUT);
  
  unsigned int myDC = 0;
  softPwmCreate(PWMLED,myDC,100);

  for(;;){
    printf("myDC: %d\n",myDC);
    
    waitForRisingEdge(0);
    digitalWrite(LED,HIGH);
    delay(1000);
    printf("changing!\n");
    waitForRisingEdge(0);
    digitalWrite(LED,LOW);
    delay(1000);
    printf("changing!\n");

    if(myDC == 100)
    myDC = 0;
    else
    myDC += 10;

    softPwmWrite(PWMLED,myDC);
    
  }
}