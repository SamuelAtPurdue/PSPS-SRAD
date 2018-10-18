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
  <li> Redundant Systems for critical flight measurements and communication</li>
  <li> A custom build PCB sled capable of handling the G forces of flight</li>
  <li> A User Interface which can interpret data and send information and commands to the rocket</li>
  <li> A method to interpret the critical data in flight in order to trigger charges for chute deployment and recovery</li>
  <li> A reliable communication protocol for sending raw flight data back to the computer on the ground</li>
  <li> Full communication and control from outside the rocket</li>
  <li> Easy access for charging
  <li> Adaptability, robust code, and easy to interpret documentation</li>
</ul>
</p>
<br>
<h3><b>Secondary Mission Requirements</h3>
<hr>
<p>
The following are not mission critical but are goals but are planned for the future:
<ul>
  <li> Easy to interpret graphical representation of flight data</li>
  <li> Prediction of flight based on weather conditions</li>
  <li> Electronic Buzzer for easier recovery</li>
  <li> Flight prediction and predicted failures</li>
  <li> Noncritical flight data such orientation</li>
</ul>
</p>
<br>
<h3>Restrictions</h3>
<hr>
<p>
  The constraints are as follows:
  <ul>
    <li> Cost: TBD</li>
    <li> Flyability: Must fit in a 6 inch radius fuselage</li>
    <li> Length and weight should be minimized</li>
  </ul>
</p>
<br>
<h3>Components</h3>
<hr>
<p>
  TBD
</p>
<br>
<h3>Code Dependencies</h3>
<hr>
<p>
  <ul>
    <li><b>json-simple:</b> https://github.com/fangyidong/json-simple</li>
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
          <li>Abstract Project Outline: 10/31/18</li>
          <li>Detailed Project Outline: 11/30/18</li>
          <li>Small Scale Test: 12/15/18</li>
          <li>v1 Full Scale Test: 12/31/18</li>
        </ul>
      <br>
      Project Lead: Samuel Hild<br>
    </p>
  </li>
  <li> 
    <h4> Flight computer hardware</h4>
    <p> 
      Description: Flight Computer Hardware is tasked with construction of the electronic components of the flight computer including sensors, tracking, and main processor. <br>
      Timeline: TBD<br>
      Project Lead: Jeff Kaji<br>
    </p>
  </li>
  <li> 
    <h4> Flight Data Collection</h4>
    <p>
      Description: Flight Data Collection is tasked with collection of flight and status data from the flight computer.<br>
      Timeline: TBD<br>
      Project Lead: Jeff Kaji<br>
    </p>
  </li>
  <li> 
    <h4> Radio Hardware and Operation</h4>
    <p> 
      Description: Radio Hardware and Operations is tasked with building and operating a radio capable of receiving transmissions from the rocket throughout the duration of flight while operating within the legal regulations.<br>
      Timeline: TBD<br>
      Project Lead: Arpit Amin
    </p>
  </li>
  <li> 
    <h4> Flight sled Hardware</h4>
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
