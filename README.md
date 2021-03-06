# PSPS-SRAD
<hr>
<h2>Purdue Space Program Solids - Avionics SRAD</h2>
<hr>
<br>
<h3><b>Vision</h3>
<hr>
<p>
Our Mission is to create a student researched and designed avionics sled capable of:
<ul>
  <li> Recording and Transmitting all relevant flight data</li>
  <li> Being easily accessed before and during flight through a wireless connection</li>
  <li> Meeting and exceeding the capabilities of the previously flown COTS sled</li>
</ul>
</p>
<br>
<h3><b>Primary Mission Requirements</h3>
<hr>
<p>
The Primary Mission Requirements are as follows:
<ul>
  <li> A method to interpret the critical data in flight in order to trigger charges for chute deployment and recovery</li>
  <li> Redundant Systems for critical flight measurements and communication</li>
  <li> Adaptability, robust code, and easy to interpret documentation</li>
  <li> A User Interface which can interpret data from the rocket</li>
  <li> A reliable communication protocol for sending raw flight data back to the computer on the ground</li>
  <li> Easy access for charging</li>
  <li> Full communication and control from outside the rocket</li>  
</ul>
</p>
<br>
<h3><b>Secondary Mission Requirements</h3>
<hr>
<p>
The following are not mission critical but are goals but are planned for the future:
<ul>
  <li> Ability to send configuration information to the rocket</li>
  <li> Easy to interpret graphical representation of flight data</li>
  <li> Prediction of flight based on weather conditions</li>
  <li> Electronic Buzzer for easier recovery</li>
  <li> Flight prediction and predicted failures</li>
  <li> Noncritical flight data such orientation</li>
  <li> A custom build PCB sled capable of handling the G forces of flight</li>
</ul>
</p>
<br>
<h3>Restrictions</h3>
<hr>
<p>
  The constraints are as follows:
  <ul>
    <li> Cost: See Budget</li>
    <li> Flyability: Must fit in a 6 inch radius fuselage</li>
    <li> Length and weight should be minimized</li>
  </ul>
</p>
<br>
<h3>Components</h3>
<hr>
<p>
  <b>System 1</b>
  <ul>
    <li>Radio: XBee Pro 900</li>
    <li>Processor: Raspberry Pi Zero W</li>
    <li>Timer: Attiny 85</li>
    <li>Altimeter: Bmp280</li>
    <li>Real Time Clock: DS3231</li>
    <li>Accelerometer: Lsm9DS1</li>
    <li>GPS: Ultimate GPS</li>
  </ul>
  <b>System 2</b>
  <ul>
    <li>Radio: XBee Pro 900 (shared with system 1)</li>
    <li>Processor: Raspberry Pi Zero W</li>
    <li>Altimeter: TBD</li>
    <li>Accelerometer: TBD</li>
    <li>GPS: TBD</li>
  </ul>
</p>
<br>
<h3>Code Dependencies</h3>
<hr>
<p>
  <ul>
    <li><b>json-simple:</b> https://github.com/fangyidong/json-simple</li>
    <li><b>junit: </b> https://junit.org/junit4/</li>
    <li><b>xbee java libraries: </b> https://www.digi.com/resources/documentation/digidocs/90001438/</li>
    <li><b>jSerialComm: </b> https://github.com/Fazecast/jSerialComm</li>
  </ul>
</p>
<br>
<br>
<h3>Project Structure</h3>
<hr>
<p>
This project is organized into 7 main team roles:
<ul>
  <li> 
    <h4> Data Transmission and Interpretation</h4>
    <p> 
      Description: The Data Transmission and Interpretation role is tasked with developing and maintaining a effective way to collect, store, and use data received from the rocket.<br>
      Timeline: 
        <ul>
          <li>Requirements 1st Draft: 10/19/18 - Completed 10/18/2018</li>
          <li>Abstract Project Outline: 10/31/18 - Completed 10/25/2018</li>
          <li>Detailed Project Outline: 11/30/18 - Completed 11/11/2018</li>
          <li>Small Scale Test: 12/15/18 - In Progress</li>
          <li>v1 Full Scale Test: 12/31/18</li>
        </ul>
      <br>
      Project Lead: Samuel Hild<br>
    </p>
  </li>
  <li> 
    <h4> Flight computer System 1 hardware</h4>
    <p> 
      Description: Flight Computer Hardware is tasked with construction of the electronic components of the flight computer including sensors, tracking, and main processor. <br>
      Timeline: TBD<br>
      Project Lead: Jeff Kaji<br>
    </p>
  </li>
  <li> 
    <h4> Flight Computer System 1 Software</h4>
    <p>
      Description: Flight Computer Software is tasked with collection of flight and status data from the flight computer.<br>
      Timeline: TBD<br>
      Project Lead: Jeff Kaji<br>
    </p>
  </li>
  <li> 
    <h4> Radio Hardware and Operation</h4>
    <p> 
      Description: Radio Hardware and Operations is tasked with building and operating a radio capable of receiving transmissions from the rocket throughout the duration of flight while operating within the legal regulations.<br>
      Timeline: Waiting on Radio<br>
      Project Lead: Arpit Amin
    </p>
  </li>
  <li> 
    <h4> Flight Sled Hardware</h4>
    <p> 
      Description: Flight Sled Hardware is tasked with creating a 3d model of a flight sled to be flown and choosing the correct materials to withstand the flight.<br>
      Timeline: TBD<br>
      Project Lead: Arpit Amin<br>
    </p>
  </li>
  <li> 
    <h4> Ground Station Software</h4>
    <p>
      Description: The Ground Station Software role is tasked with building and maintaining an easy to use, understand, and maintain user interface for use by the groundstation. This will include data displays and and easy way to send commands to the rocket.<br>
      Timeline: TBD<br>      
      Project Lead: Mark Batistich<br>
    </p>
  </li>
  <li> 
    <h4> Systems Coordinator</h4>
    <p> 
      Description: The System Coordinator are tasked with maintaining communication between the other teams and making sure everything runs together and is well documented.<br>
      Timeline: Required Throughout duration of the project.<br>
      Project Lead: Samuel Hild<br>
    </p>  
  </li>
</ul>
</p>
<br>
