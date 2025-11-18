#!/bin/bash
echo "=== PROVISIONAMENTO INFRAESTRUTURA BASE (SQL + ACR) ==="

# Resource Group
if ! az group show --name MetaFlowGroup &>/dev/null; then
    echo "Criando Resource Group..."
    az group create --name MetaFlowGroup --location brazilsouth
else
    echo "Resource Group já existe."
fi

# SQL Server
if ! az sql server show --name metaflow-sql-server-rm557702 --resource-group MetaFlowGroup &>/dev/null; then
    echo "Criando SQL Server..."
    az sql server create \
        --resource-group MetaFlowGroup \
        --name metaflow-sql-server-rm557702 \
        --location brazilsouth \
        --admin-user metaflowadmin \
        --admin-password "Michele2006@"
else
    echo "SQL Server já existe."
fi

# Firewall Rule
echo "Configurando firewall..."
az sql server firewall-rule create \
    --resource-group MetaFlowGroup \
    --server metaflow-sql-server-rm557702 \
    --name AllowAll \
    --start-ip-address 0.0.0.0 \
    --end-ip-address 255.255.255.255 \
    --output none

# SQL Database
if ! az sql db show --name metaflow-db --server metaflow-sql-server-rm557702 --resource-group MetaFlowGroup &>/dev/null; then
    echo "Criando SQL Database..."
    az sql db create \
        --resource-group MetaFlowGroup \
        --server metaflow-sql-server-rm557702 \
        --name metaflow-db \
        --service-objective Basic \
        --max-size 2GB
else
    echo "SQL Database já existe."
fi

# Azure Container Registry (ACR)
if ! az acr show --name metaflowacrrm557702 --resource-group MetaFlowGroup &>/dev/null; then
    echo "Criando Container Registry..."
    az acr create \
        --resource-group MetaFlowGroup \
        --name metaflowacrrm557702 \
        --sku Basic \
        --admin-enabled true
else
    echo "Container Registry já existe."
fi

echo "=== INFRAESTRUTURA BASE CONCLUÍDA ==="
echo "SQL Server: metaflow-sql-server-rm557702.database.windows.net"
echo "Database: metaflow-db"
echo "ACR: metaflowacrrm557702.azurecr.io"