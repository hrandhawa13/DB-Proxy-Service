DB-Proxy-Service
Microservice in AWS that connects to on prem oracle database.

This will be a microservice in AWS that will connect to on prem oracle database. It should have a connection pool per tenant. Lambdas will reach out to this service, provide tenant ID and request for a connection to that tenant. This service will then return the connection to the on prem database. Following are some of the initial thoughts:

Security: VPN, Firewall, TLS
Network: Latency, Bandwidth
Connection Pooling: Pool size, idle connections, connections reuse
Health checks of connections, set timeout on database operations?
Stale Connections: Recycle connections to avoid memory leaks or stale sessions
Error handling if database is down
Connection pool metrics- active, idle connections, request latency, alerts if possible