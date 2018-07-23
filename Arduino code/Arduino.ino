int lrd=17;     // Tune motor by changing this value

int leftbreak = 2;
int leftphase = 4;
int leftenable = 6;
int leftmode = 9;
int rightbreak = 10;
int rightphase = 5;
int rightenable = 3;
int rightmode = 11;

int masterinitialspeed = lrd+40;
int l=masterinitialspeed;
int r=l-lrd;
int s=0;

void setup() {
  Serial1.begin(9600);
  pinMode(leftbreak, OUTPUT);
  pinMode(leftphase, OUTPUT);  
  pinMode(leftenable, OUTPUT);
  pinMode(leftmode, OUTPUT);
  pinMode(rightbreak, OUTPUT);
  pinMode(rightphase, OUTPUT);
  pinMode(rightenable, OUTPUT);
  pinMode(rightmode, OUTPUT);
  
  }

void loop() {
  
  if(Serial1.available()>0){
    char data = Serial1.read();
    if(data == 'w'){
        Serial.print(data);
        forward();}
        
        else if(data == 'a'){
      Serial.print(data);
        left();}
        
        else if(data == 'd'){
      Serial.print(data);
        right();}
        
        else if(data == 'x'){
      Serial.print(data);
        backward();}
        
        else if(data == 'q'){
      Serial.print(data);
        forwardLeft();}
        
        else if(data == 'e'){
      Serial.print(data);
        forwardRight();}

        else if(data == 'z'){
      Serial.print(data);
        backwardLeft();}

        else if(data == 'c'){
      Serial.print(data);
        backwardRight();}

        else if(data == 'u'){
      Serial.print(data);
        FL();}

        else if(data == 'i'){
      Serial.print(data);
        FR();}

        else if(data == 'o'){
      Serial.print(data);
        BL();}

        else if(data == 'p'){
      Serial.print(data);
        BR();}
        
        else if(data == 'f'){
      Serial.print(data);
        fly();}

        else if(data == 'g'){
      Serial.print(data);
        rush();}

        else if(data == 'h'){
      Serial.print(data);
        nom();}

        else if(data == 'j'){
      Serial.print(data);
        chill();}

        else if(data == 'k'){
      Serial.print(data);
        fine();}
        
    else if(data=='s'){
      Serial.print(data);
        stop();}
    delay(10);
  }
}
void forward() {
  
  digitalWrite(leftphase, LOW);
  analogWrite(leftenable, l);
  digitalWrite(leftmode, LOW);
  analogWrite(rightenable,r);
  digitalWrite(rightphase, HIGH);
  digitalWrite(rightmode, LOW);
  digitalWrite(rightbreak, HIGH);
  digitalWrite(leftbreak, HIGH);
   }
void backward(){
  digitalWrite(leftphase, HIGH);
  analogWrite(leftenable, l);
  digitalWrite(leftmode, LOW);
  analogWrite(rightenable,r);
  digitalWrite(rightphase, LOW);
  digitalWrite(rightmode, LOW);
  digitalWrite(leftbreak, HIGH);
  digitalWrite(rightbreak, HIGH);
   
   }
void left(){
  digitalWrite(leftphase, HIGH);
  analogWrite(leftenable, l);
   digitalWrite(rightmode, LOW);
  digitalWrite(leftmode, LOW);
  analogWrite(rightenable,r);
  digitalWrite(rightphase, HIGH);
  digitalWrite(leftbreak, HIGH);
  digitalWrite(rightbreak, HIGH);
   digitalWrite(rightmode, LOW);
   }
void right(){
  digitalWrite(leftphase, LOW);
  analogWrite(leftenable, l);
  digitalWrite(rightmode, LOW);
  digitalWrite(leftmode, LOW);
  analogWrite(rightenable,r);
  digitalWrite(rightphase, LOW);
  digitalWrite(leftbreak, HIGH);
  digitalWrite(rightbreak, HIGH);
   
   }
void forwardLeft() {
 
 
  digitalWrite(leftphase, LOW);
  analogWrite(leftenable, l);
  digitalWrite(rightmode, LOW);
  digitalWrite(leftmode, LOW);
  analogWrite(rightenable,r-30);
  digitalWrite(rightphase, HIGH);
  digitalWrite(leftbreak, HIGH);
  digitalWrite(rightbreak, HIGH);
  
   }
void forwardRight() {
  
  digitalWrite(leftphase, LOW);
  analogWrite(leftenable, l-30);
  digitalWrite(rightmode, LOW);
  digitalWrite(leftmode, LOW);
  analogWrite(rightenable,r);
  digitalWrite(rightphase, HIGH);
  digitalWrite(leftbreak, HIGH);
  digitalWrite(rightbreak, HIGH);
   
   }
void backwardLeft(){
 
  digitalWrite(leftphase, HIGH);
  analogWrite(leftenable, l);
  digitalWrite(rightmode, LOW);
  digitalWrite(leftmode, LOW);
  analogWrite(rightenable,r-15);
  digitalWrite(rightphase, LOW);
  digitalWrite(leftbreak, HIGH);
  digitalWrite(rightbreak, HIGH);
   
   }
void backwardRight(){
  
  digitalWrite(leftphase, HIGH);
  analogWrite(leftenable, l-15);
  digitalWrite(rightmode, LOW);
  digitalWrite(leftmode, LOW);
  analogWrite(rightenable,r);
  digitalWrite(rightphase, LOW);
  digitalWrite(leftbreak, HIGH);
  digitalWrite(rightbreak, HIGH);
  
   }
void FL(){
  digitalWrite(leftphase, LOW);
  analogWrite(leftenable, l);
  digitalWrite(leftbreak, HIGH);
  digitalWrite(leftmode, LOW);
}
void FR(){
  digitalWrite(rightphase, HIGH);
  analogWrite(rightenable,r);
  digitalWrite(rightbreak, HIGH);
  digitalWrite(rightmode, LOW);
}
void BL(){
  digitalWrite(leftphase, HIGH);
  analogWrite(leftenable, l);
  digitalWrite(leftbreak, HIGH);
  digitalWrite(leftmode, LOW);
}
void BR(){
  digitalWrite(rightphase, LOW);
  analogWrite(rightenable,r);
  digitalWrite(rightbreak, HIGH);
  digitalWrite(rightmode, LOW);
}
void fly(){
  l=lrd;
  r=l-lrd;
}
void rush(){
  l=65;
  r=l-lrd;
}
void nom(){
  l=125;
  r=l-lrd;
}
void chill(){
  l=170;
  r=l-lrd;
}
void fine(){
  l=220;
  r=l-lrd;
}

void stop(){
  digitalWrite(leftbreak, LOW);
  digitalWrite(rightbreak, LOW);
  }


