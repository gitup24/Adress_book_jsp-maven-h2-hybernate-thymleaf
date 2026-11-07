JUNIE:
Fix "module not loaded" error running index.jsp in IntelliJ
Solution 1: Run via the Cargo Maven Plugin (Recommended)
Your pom.xml already has the cargo-maven3-plugin configured to automatically download and run Apache Tomcat 10.
1.
Via Terminal: Open the terminal in IntelliJ and run:
mvn clean package cargo:run
2.
Via IntelliJ Maven Tool Window:
◦
Open the Maven tool window on the right side of IntelliJ.
◦
Navigate to carnet-adresses -> Plugins -> cargo -> double-click cargo:run.
3.
Open your browser and navigate to:
http://localhost:8080/carnet/

Solution 2: Reload the Maven Project in IntelliJ
If IntelliJ lost track of the project modules:
1.
Right-click pom.xml in the project explorer.
2.
Select Maven -> Reload Project (or click the floating Maven sync button / icon in the top right).
3.
Check File -> Project Structure (Ctrl+Alt+Shift+S or Cmd+;) -> Modules and ensure the carnet-adresses module is listed and not marked as ignored or unloaded.

Solution 3: Configure Tomcat Run Configuration (IntelliJ IDEA Ultimate)
If you are using IntelliJ IDEA Ultimate and want to use IntelliJ's built-in application server runner:
1.
Go to Run -> Edit Configurations...
2.
Click + (Add New Configuration) -> select Tomcat Server -> Local.
3.
In the Deployment tab, click + -> Artifact... -> choose carnet:war or carnet:war exploded.
4.
Set the Application context (e.g., /carnet or /).
5.
Click Apply and then Run