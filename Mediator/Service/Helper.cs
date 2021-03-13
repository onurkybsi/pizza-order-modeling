using System;
using System.IO;
using Microsoft.Extensions.Configuration;

namespace PizzaStore.Service
{
    public static class Helper
    {
        public static void LoadEnvFile(string envFilePath)
        {
            if (!File.Exists(envFilePath))
                return;

            foreach (var line in File.ReadAllLines(envFilePath))
            {
                var parts = line.Split(
                    '=',
                    StringSplitOptions.RemoveEmptyEntries);

                if (parts.Length != 2)
                    continue;

                Environment.SetEnvironmentVariable(parts[0], parts[1]);
            }
        }
        public static IConfiguration CreateConfigurationWithEnvironmentVariables(string appSettingsPath, string environment)
            => new ConfigurationBuilder()
                .SetBasePath(appSettingsPath)
                .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
                .AddJsonFile($"appsettings.{environment}.json", optional: true)
                .AddEnvironmentVariables()
                .AddEnvironmentVariables()
                .Build();
    }
}