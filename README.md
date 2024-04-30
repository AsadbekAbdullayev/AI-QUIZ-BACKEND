# Project Documentation

## Overview

This project is developed using Spring Boot and incorporates various technologies including Azure OpenAI service, Microsoft Visual Studio, and GitHub. It is designed to leverage the robust capabilities of these platforms to deliver a high-performance and scalable application.

## Followings are used to create our project and based on project requirements 

- **IDE:** Microsoft Visual Studio with java and spring boot plugin
- **Version Control:** GitHub
- **Cloud Services:** Azure OpenAI Service

## Configuration

### Environment Variables

To configure the project correctly, set the following environment variables in your development environment:

- `GOOGLE_CLIENT_SECRET`: Used for authentication with Google services.
    - Value: `GOCSPX*************`
- `LINKEDIN_CLIENT_SECRET`: Used for LinkedIn integration.
    - Value: `*******************`
- `ONEID_CLIENT_SECRET`: Utilized for OneID service connections.
    - Value: `XD9rjt*************`
- `AZURE_OPENAI_KEY`: Key for accessing Azure OpenAI services.
    - Value: `sk-****************`
- `SMTP_PASSWORD`: Password for SMTP email service.
    - Value: `zar***************`

**Note:** Ensure these variables are stored securely and are not exposed in your codebase or version control system.

## Security Practices

- Avoid hardcoding sensitive information such as keys and passwords in your source code.
- Use environment variables to manage sensitive data securely.
- Ensure that access to these environment variables is restricted to prevent unauthorized access.

## Additional Information

For further details on how to set up and manage your development environment with Visual Studio, refer to the [official Visual Studio documentation](https://docs.microsoft.com/en-us/visualstudio/).

For more information on managing projects on GitHub, consult the [GitHub documentation](https://docs.github.com/).
