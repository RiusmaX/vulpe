# Vulpe Framework ;) Quick and Smart #
<p>
Vulpe is an Open Source project to create applications quickly and efficiently on the Java platform, integrating Open Source tools (eg: Struts2, EJB3, Spring, db4o, and many others) to help in development.<br>
</p>
<p>
It provides templates for creating: CRUD (Master-Detail, Master-Detail-Subdetail), Selection, Sorting / Paging, Tabular, Report, Download, Upload.<br>
</p>
<p>
His presentation layer is AJAX based, so the applications have rich interfaces, more usability and nice look.<br>
</p>
<p>
He creates architectural designs based on simple models and highly scalable, focusing development to the business rules of the project.<br>
</p>
<p>
Vulpe is modularized, so you can use only what is interesting for your project, for example: Layer Model with management to db4o or Layer Control with Struts2. Anyway, it gives you many possibilities for development and customization of applications.<br>
</p>
<p>
Give an opportunity for Vulpe! You will not be disappointed.<br>
</p>

---

<p>
Features in Vulpe 1.0.x:<br>
<ul><li>Maven 3 Integration<br>
</li><li>Spring Annotations<br>
</li><li>Web Frameworks support: Struts 2<br>
</li><li>JDK 5, Annotations, JSP 2.0, Servlet 2.4<br>
</li><li>EJB3 Support<br>
</li><li>Web Service Support<br>
</li><li>JPA Support<br>
</li><li>db4o Support<br>
</li><li>Generic CRUD, Selection, Sorting / Paging, Tabular, Report on backend and frontend<br>
</li><li>Full Eclipse support<br>
</p>
<hr />
<h2>First steps to play with Me!</h2>
<h3>Creating the first project ;)</h3>
<p>
Before we start you need to configure Maven for him to access the repository containing my codes. To this add the following lines to the file <b>conf/settings.xml</b> of Maven.<br>
</p>
<pre><code>&lt;repository&gt;<br>
&lt;id&gt;activethread&lt;/id&gt;<br>
&lt;name&gt;Active Thread Maven Repository&lt;/name&gt;<br>
&lt;url&gt;http://repository.activethread.com.br/maven2&lt;/url&gt;<br>
&lt;snapshots&gt;<br>
&lt;updatePolicy&gt;interval:60&lt;/updatePolicy&gt;<br>
&lt;/snapshots&gt;<br>
&lt;/repository&gt;<br>
</code></pre>
<p>
Configured with the repository we're ready to go!<br>
</p>
<p>
I use <b>archetypes</b> from Maven to create projects. Take a look at the list below, you can choose what best suit your needs:<br>
</p>
</li><li>Struts 2, db4o and POJO<br>
</li><li>Struts 2, db4o and EJB3<br>
</li><li>Struts 2, JPA and POJO<br>
</li><li>Struts 2, JPA and EJB3<br>
<p>
After choosing the archetype is just run the Maven command below, changing the name of the archetype and initial data on their application (e.g. the package and the name of the application).<br>
</p>
<pre><code>mvn archetype:generate -B -DarchetypeGroupId=org.vulpe.archetypes <br>
-DarchetypeArtifactId=vulpe-struts2-db4o-archetype -DarchetypeVersion=1.0.1 <br>
-DgroupId=your.package -DartifactId=your_application -Dversion=1.0 <br>
-Dpackage=your.package.your_application<br>
</code></pre>
<p>
Ready! Now just import the project created in Eclipse or your favorite IDE.<br>
</p>
<h2>Download Vulpe</h2>
<a href='https://s3.amazonaws.com/vulpeframework/all-in-one/vulpe-all-in-one-1.0-win32.exe'>Vulpe 1.0 All-in-one (Windows 32bit)</a>
<br>
<a href='https://s3.amazonaws.com/vulpeframework/all-in-one/vulpe-all-in-one-1.0.x-win64.exe'>Vulpe 1.0.x All-in-one (Windows 64bit)</a>
<br>
<a href='https://s3.amazonaws.com/vulpeframework/all-in-one/vulpe-all-in-one-1.0.x-macosx.dmg'>Vulpe 1.0.x All-in-one (Mac OS X)</a>
<br>
<a href='https://s3.amazonaws.com/vulpeframework/all-in-one/vulpe-all-in-one-1.0-linux-x86.tar.gz'>Vulpe 1.0 All-in-one (Linux 32bit)</a>
<br>
<a href='https://s3.amazonaws.com/vulpeframework/all-in-one/vulpe-all-in-one-1.0-linux-x64.tar.gz'>Vulpe 1.0 All-in-one (Linux 64bit)</a>
<br>
<br>
<h3>Support This Project</h3>
If you like and want to contribute:<br>
<a href='http://sourceforge.net/donate/index.php?group_id=320794'><img src='http://images.sourceforge.net/images/project-support.jpg' alt='Support This Project' border='0' width='88' height='32' /></a>