FROM payara/server-full:5.2021.1

COPY dist/SkillBridge.war /opt/payara/deployments/SkillBridge.war

EXPOSE 8080
