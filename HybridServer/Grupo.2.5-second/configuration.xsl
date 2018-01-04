<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:l="http://www.esei.uvigo.es/dai/hybridserver">
	<xsl:output method="html" indent="yes" encoding="utf-8" />
	<xsl:template match="/">
		<xsl:text disable-output-escaping="yes">&lt;!DOCTYPE HTML&gt;</xsl:text>
		<html>
			<head>
				<title>Configuration</title>
			</head>
			<body>
				<div>
					<h2>Connections</h2>
					<div id="connections">
						<xsl:apply-templates select="l:configuration/l:connections" />
					</div>

					<h2>Database</h2>
					<div id="database">
						<xsl:apply-templates select="l:configuration/l:database" />
					</div>

					<h2>Servers</h2>
					<div id="servers">
						<xsl:apply-templates select="l:configuration/l:servers" />
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="l:connections">
		<div>
			<h2>
				HTTP:
				<xsl:value-of select="l:http" />
				WebService:
				<xsl:value-of select="l:webservice" />
				Numero de conexiones:
				<xsl:value-of select="l:numClients" />
			</h2>
		</div>
	</xsl:template>
	<xsl:template match="l:database">
		<div>
			<h2>
				Usuario:
				<xsl:value-of select="l:user" />
				Contraseña:
				<xsl:value-of select="l:password" />
				URL:
				<xsl:value-of select="l:url" />
			</h2>
		</div>
	</xsl:template>
	<xsl:template match="l:servers">
		<xsl:for-each select="l:server">
			<h3>
				Server:
				<xsl:value-of select="@name" />
				.
			</h3>
			<p>
				WSDL:
				<xsl:value-of select="@wsdl" />
				.
			</p>
			<p>
				NameSpace:
				<xsl:value-of select="@namespace" />
				.
			</p>
			<p>
				Service
				<xsl:value-of select="@service" />
				.
			</p>
			<p>
				httpAddress
				<xsl:value-of select="@httpAddress" />
				.
			</p>

		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>