FROM mcr.microsoft.com/dotnet/core/sdk:3.1 AS build
WORKDIR /mediator

COPY PizzaStore.csproj ./
RUN dotnet restore
COPY . ./

RUN dotnet publish -c Release -o out

FROM mcr.microsoft.com/dotnet/core/aspnet:3.1 AS runtime
WORKDIR /mediator

COPY --from=build /mediator/out ./
ENTRYPOINT ["dotnet", "PizzaStore.dll"]