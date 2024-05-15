<helpset version="1.0">
	<title>Sistema de Ayuda</title>
	<maps>
		<!-- Página por defecto al mostrar la ayuda -->
		<homeID>ayudaboms</homeID>
		<!-- Qué mapa deseamos -->
		<mapref location="map.jhm"/>
	</maps>

	<!-- Las vistas que deseamos mostrar en la ayuda -->
	<!-- La tabla de contenidos -->
	<view>
		<name>Tabla Contenidos</name>
		<label>Tabla de Contenidos</label>
		<type>javax.help.TOCView</type>
		<data>toc.xml</data>
	</view>
	
	<!-- El índice -->
	<view>
		<name>Índice</name>
		<label>El índice</label>
		<type>javax.help.IndexView</type>
		<data>indice.xml</data>
	</view>
		
	<!-- La pestaña de búsqueda -->
	<view>
		<name>Buscar</name>
		<label>Buscar</label>
		<type>javax.help.SearchView</type>
		<data engine="com.sun.java.help.search.DefaultSearchEngine">
			JavaHelpSearch
		</data>
	</view>
</helpset>