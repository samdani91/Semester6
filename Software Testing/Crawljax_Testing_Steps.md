# Crawljax Testing Steps

Follow these steps to set up and run Crawljax for testing:

1. **Clone the Repository**  
   ```bash
   git clone https://github.com/crawljax/crawljax.git
   ```

2. **Navigate to the Project Directory**  
   ```bash
   cd crawljax
   ```

3. **Build the Project**  
   ```bash
   mvn clean install -DskipTests
   ```

4. **Navigate to the CLI Directory**  
   ```bash
   cd cli
   ```

5. **Update the `pom.xml` File**  
   Replace or ensure the `pom.xml` in the `cli` directory contains the following configuration:

   ```xml
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>

       <parent>
           <groupId>com.crawljax</groupId>
           <artifactId>crawljax-parent-pom</artifactId>
           <version>5.2.4-SNAPSHOT</version>
       </parent>

       <artifactId>crawljax-cli</artifactId>
       <packaging>jar</packaging>
       <name>Crawljax CLI</name>
       <description>The Crawljax command line interface</description>

       <dependencies>
           <dependency>
               <groupId>${project.groupId}</groupId>
               <artifactId>crawljax-core</artifactId>
               <version>${project.version}</version>
           </dependency>

           <dependency>
               <groupId>commons-cli</groupId>
               <artifactId>commons-cli</artifactId>
               <version>1.5.0</version>
           </dependency>

           <dependency>
               <groupId>commons-validator</groupId>
               <artifactId>commons-validator</artifactId>
               <version>1.7</version>
           </dependency>

           <dependency>
               <groupId>com.crawljax.plugins</groupId>
               <artifactId>crawloverview-plugin</artifactId>
               <version>${project.version}</version>
           </dependency>
       </dependencies>

       <build>
           <resources>
               <resource>
                   <directory>src/main/resources</directory>
                   <filtering>true</filtering>
               </resource>
           </resources>

           <plugins>
               <plugin>
                   <groupId>org.apache.maven.plugins</groupId>
                   <artifactId>maven-shade-plugin</artifactId>
                   <version>3.4.1</version>
                   <executions>
                       <execution>
                           <phase>package</phase>
                           <goals>
                               <goal>shade</goal>
                           </goals>
                           <configuration>
                               <transformers>
                                   <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                       <mainClass>com.crawljax.cli.JarRunner</mainClass>
                                   </transformer>
                               </transformers>
                           </configuration>
                       </execution>
                   </executions>
               </plugin>
           </plugins>
       </build>
   </project>
   ```

6. **Remove Signature Files from the JAR**  
   ```bash
   zip -d target/crawljax-cli-5.2.4-SNAPSHOT.jar META-INF/*.RSA META-INF/*.SF
   ```

7. **Run Crawljax**  
   Ensure ChromeDriver and Google Chrome are installed and accessible at the specified paths. Then execute:

   ```bash
   java -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver -Dwebdriver.chrome.binary=/usr/bin/google-chrome -jar target/crawljax-cli-5.2.4-SNAPSHOT.jar http://127.0.0.1:3000 ./output
   ```