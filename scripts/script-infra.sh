#!/bin/bash
echo "=== PROVISIONAMENTO METAFLOW (PaaS) ==="

# ==========================
# VARIÁVEIS
# ==========================
RG="rg-metaflow"
LOCATION="brazilsouth"
SQL_SERVER="sql-metaflow-557702"
SQL_ADMIN="admin-metaflow"
SQL_PASSWORD="Michelle2006@"
SQL_DB="db-metaflow"
PLAN_NAME="metaflow-plan"
WEBAPP_NAME="app-metaflow-rm557702"

echo "--- Criando Resource Group ---"
if ! az group show --name $RG &>/dev/null; then
    az group create --name $RG --location $LOCATION
else
    echo "Resource Group já existe."
fi

echo "--- Criando Servidor SQL ---"
if ! az sql server show --name $SQL_SERVER --resource-group $RG &>/dev/null; then
    az sql server create \
        --name $SQL_SERVER \
        --resource-group $RG \
        --location $LOCATION \
        --admin-user $SQL_ADMIN \
        --admin-password "$SQL_PASSWORD"
else
    echo "SQL Server já existe."
fi

echo "--- Criando Regra de Firewall (DEV)---"
az sql server firewall-rule create \
    --resource-group $RG \
    --server $SQL_SERVER \
    --name AllowAllDev \
    --start-ip-address 0.0.0.0 \
    --end-ip-address 255.255.255.255 \
    --output none

echo "--- Criando Banco de Dados ---"
if ! az sql db show --resource-group $RG --server $SQL_SERVER --name $SQL_DB &>/dev/null; then
    az sql db create \
        --resource-group $RG \
        --server $SQL_SERVER \
        --name $SQL_DB \
        --service-objective Basic \
        --backup-storage-redundancy Local
else
    echo "Banco já existe."
fi

echo "--- Criando App Service Plan ---"
if ! az appservice plan show --name $PLAN_NAME --resource-group $RG &>/dev/null; then
    az appservice plan create \
        --name $PLAN_NAME \
        --resource-group $RG \
        --location $LOCATION \
        --sku B1 \
        --is-linux
else
    echo "App Service Plan já existe."
fi

echo "--- Criando Web App ---"
if ! az webapp show --name $WEBAPP_NAME --resource-group $RG &>/dev/null; then
    az webapp create \
        --resource-group $RG \
        --plan $PLAN_NAME \
        --name $WEBAPP_NAME \
        --runtime "JAVA:21-java21"
else
    echo "Web App já existe."
fi

echo "--- Definindo Variáveis de Ambiente ---"
az webapp config appsettings set \
    --name $WEBAPP_NAME \
    --resource-group $RG \
    --settings \
        SPRING_DATASOURCE_URL="jdbc:sqlserver://$SQL_SERVER.database.windows.net:1433;database=$SQL_DB;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;" \
        SPRING_DATASOURCE_USERNAME="$SQL_ADMIN" \
        SPRING_DATASOURCE_PASSWORD="$SQL_PASSWORD" \
        SPRING_JPA_HIBERNATE_DDL_AUTO="update" \
        SPRING_JPA_SHOW_SQL="true" \
        SPRINGDOC_API_DOCS_PATH="/v3/api-docs" \
        SPRINGDOC_SWAGGER_UI_PATH="/swagger-ui/index.html" \
        SERVER_PORT="80"

echo "=== PROVISIONAMENTO FINALIZADO COM SUCESSO ==="
