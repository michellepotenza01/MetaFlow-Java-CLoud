#!/bin/bash
set -e

# Parâmetros principais do App e do banco - METAFLOW
RESOURCE_GROUP_APP="rg-metaflow"
RESOURCE_GROUP_DB="rg-metaflow-db"
LOCATION="brazilsouth"
APP_SERVICE_PLAN="metaflow-plan"
WEBAPP_NAME="app-metaflow-rm557702"
PLAN_SKU="B1"
RUNTIME="JAVA:21-java21"

SQL_SERVER_NAME="sql-metaflow-557702"
SQL_DB_NAME="db-metaflow" 
SQL_ADMIN_USER="admin-metaflow"
SQL_ADMIN_PASSWORD="Michelle2006@"

echo "=== INICIANDO PROVISIONAMENTO METAFLOW ==="

# Registrar provedores necessários
az provider register --namespace Microsoft.Web --wait
az provider register --namespace Microsoft.OperationalInsights --wait
az provider register --namespace Microsoft.ServiceLinker --wait
az provider register --namespace Microsoft.Sql --wait

# Criar resource groups 
echo "Criando Resource Groups..."
az group create --name "$RESOURCE_GROUP_DB" --location "$LOCATION"
az group create --name "$RESOURCE_GROUP_APP" --location "$LOCATION"

# BANCO DE DADOS SQL SERVER
echo "Criando SQL Server..."
az sql server create \
    --name $SQL_SERVER_NAME \
    --resource-group $RESOURCE_GROUP_DB \
    --location $LOCATION \
    --admin-user $SQL_ADMIN_USER \
    --admin-password $SQL_ADMIN_PASSWORD \
    --enable-public-network true

echo "Criando Banco de Dados..."
az sql db create \
    --resource-group $RESOURCE_GROUP_DB \
    --server $SQL_SERVER_NAME \
    --name $SQL_DB_NAME \
    --service-objective Basic \
    --backup-storage-redundancy Local

# Firewall para desenvolvimento (permite todos os IPs)
echo "Configurando firewall do SQL Server..."
az sql server firewall-rule create \
    --resource-group $RESOURCE_GROUP_DB \
    --server $SQL_SERVER_NAME \
    --name AllowAllDevTEMP \
    --start-ip-address 0.0.0.0 \
    --end-ip-address 255.255.255.255

echo "⚠️  Firewall 0.0.0.0/255.255.255.255 habilitado somente para desenvolvimento."

# CRIAÇÃO DE OBJETOS E DADOS INICIAIS NO BANCO
echo "Executando script do banco de dados..."
sqlcmd \
    -S "$SQL_SERVER_NAME.database.windows.net" \
    -d "$SQL_DB_NAME" \
    -U "$SQL_ADMIN_USER" \
    -P "$SQL_ADMIN_PASSWORD" \
    -l 60 \
    -N \
    -b \
    -i "../scripts/script-bd.sql"

# Criar App Service Plan Linux
echo "Criando App Service Plan..."
az appservice plan create \
    --name "$APP_SERVICE_PLAN" \
    --resource-group "$RESOURCE_GROUP_APP" \
    --location "$LOCATION" \
    --sku "$PLAN_SKU" \
    --is-linux

# Criar Web App com runtime Java 17
echo "Criando Web App..."
az webapp create \
    --name "$WEBAPP_NAME" \
    --resource-group "$RESOURCE_GROUP_APP" \
    --plan "$APP_SERVICE_PLAN" \
    --runtime "$RUNTIME"

# Configurar variáveis de ambiente
echo "Configurando variáveis de ambiente..."
az webapp config appsettings set \
    --name "$WEBAPP_NAME" \
    --resource-group "$RESOURCE_GROUP_APP" \
    --settings \
        SPRING_DATASOURCE_URL="jdbc:sqlserver://$SQL_SERVER_NAME.database.windows.net:1433;database=$SQL_DB_NAME;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;" \
        SPRING_DATASOURCE_USERNAME="$SQL_ADMIN_USER" \
        SPRING_DATASOURCE_PASSWORD="$SQL_ADMIN_PASSWORD" \
        SPRING_JPA_HIBERNATE_DDL_AUTO="update" \
        SPRING_JPA_SHOW_SQL="true" \
        SPRINGDOC_API_DOCS_PATH="/v3/api-docs" \
        SPRINGDOC_SWAGGER_UI_PATH="/swagger-ui/index.html" \
        SERVER_PORT="80"

# Reiniciar a aplicação
echo "Reiniciando Web App..."
az webapp restart --name "$WEBAPP_NAME" --resource-group "$RESOURCE_GROUP_APP"

echo "=== PROVISIONAMENTO CONCLUÍDO ==="
echo "Web App: https://$WEBAPP_NAME.azurewebsites.net"
echo "Swagger UI: https://$WEBAPP_NAME.azurewebsites.net/swagger-ui/index.html"
echo "SQL Server: $SQL_SERVER_NAME.database.windows.net"
echo "Database: $SQL_DB_NAME"