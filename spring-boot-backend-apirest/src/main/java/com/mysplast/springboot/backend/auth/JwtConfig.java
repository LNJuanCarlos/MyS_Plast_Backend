package com.mysplast.springboot.backend.auth;

public class JwtConfig {
	
	
	public static final String RSA_PRIVATE = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEpAIBAAKCAQEAvC+APXvxPb4cbGOmHtUXnWyJRhsse0xGxR6C22sPLaV7k5wb\r\n" + 
			"KcrxTs0If00RzETCTD3smn6zCUvVovNeh42/EbGsgYqriiSs6fn2qLrfUclHWZdx\r\n" + 
			"l7BApjoWmhZRJmZ2BoGredzrJmvuW96EwFnyoMCmAr0oiklkQsiIydA7XoRzKL4A\r\n" + 
			"bMTqV3UNgOHzA76YYRP5p2enw8SYEjcxg7wkF5rU8XleWoqk7NvZgkaV/W+l/xWT\r\n" + 
			"CQufJD2Ja3T8THxHeQ0hc/sWo8lLZzO6QIlY71JFjkhIRjdmGCeFYi7czDef3vCp\r\n" + 
			"WSPzSgd5XSygk65Ws2VzRnJY6ARN/kijELTAWwIDAQABAoIBAH1purE/FHrtY8vo\r\n" + 
			"bSFHKkHDv4h1O6PAJbngp5zLMIZfLoglFQU5NrApfGw6VI4plMNy0Wf04CRl4a6D\r\n" + 
			"OpBkKt4D5s8+NywNXR9hcrzoLUQ4O9C9WPqaf7tIIx/1l4db6XRUno7MZ/pnG6cm\r\n" + 
			"GJqIV2ZCfWB957KhTqwjMOYuXCKURNVcES2Zm+Jbrs0rtp1EI27RBPCMuL2rtWsP\r\n" + 
			"jW5pWYSkHEA5j6GntPFjoDKL78ou/p+66/XOos4bbzBBoGzOZR1qddKvILRaKUBJ\r\n" + 
			"PMiipztRLewMwU1E50hY34/jd1Ez9f2AFIvgdU5L4FjG080x8uFLt2YjUE7tVQRl\r\n" + 
			"v1zqjwECgYEA8HOGpomq0JyrPI9j9O3dHp4r2l/E+pWUHyJwYeyayHUdgHH8Xe9B\r\n" + 
			"+lx0zg1MTe79h4NVySCArHpNX6TDr+bofp95uKyIB/sg/6kyr05mlEiDVNAxRY/V\r\n" + 
			"N5eBpoS1UFHm0HOQmZ9olqswH1JsK2CEgtR/3dWueja6RHXuxgfw8HsCgYEAyFrC\r\n" + 
			"OgPcvDrNd4SG2ytpMmBW+SwBz2uLEhbItQPmiK9f/qObYz7z2WDIGWrrN0HSW4ro\r\n" + 
			"xeUawhqmfYBaB+AKFCJUTH9NblepCWahzSgEPjpM/7eIbm6atIhKSyGi3ThANzQa\r\n" + 
			"NKb4PhQVp25sQxjS8Ep47w+vdUTeQlqjyb8cmaECgYBj3WdYNacnG1qXPWqjaENF\r\n" + 
			"GyZVfR8fgA5T3AbW9ODYTqPhG/ZXwS4i4FF23tkJKTBNHwP7E6+9ctI93f2USg/X\r\n" + 
			"vz5br/STc/6hkFFHtAUwoulAyXMdU/6XJyqm0UlIwateehDdbPKQWSsUZzQb0+tV\r\n" + 
			"NxKgPMZJZf94vY5c2yF7jwKBgQCRMeSrsnzXFC4uvSYV0wHrdhHRhWjSbndcZpKR\r\n" + 
			"TABXcfC5UsubQtXo5NBeSzOm2daebK6AK6YWoMWjEc8tAdmL+czpmx/8JR7lJzF+\r\n" + 
			"wc9BYXLHprQCg7ktt89PX1LtyoQO/X04RwCDzH5c1hMzFNxZMz8s0EsxSvVfVNx5\r\n" + 
			"qjwJwQKBgQC9pKGzk9ThC7dZ32l2faX2xJUwTQHEyUcSsEgLoaV1fCPmBV/NZcDd\r\n" + 
			"da0Boy4OXNuKxYY4QTGsWyiY0cTQPRKsBqVFcQnYVKYCB15wdD1GgbEDR5jktVZ6\r\n" + 
			"zs9XU6jGNxJnGgcr0CFY0MELtQeI6LR0c3nZF4+ca5bhxdRwcyxmWg==\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLIC = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvC+APXvxPb4cbGOmHtUX\r\n" + 
			"nWyJRhsse0xGxR6C22sPLaV7k5wbKcrxTs0If00RzETCTD3smn6zCUvVovNeh42/\r\n" + 
			"EbGsgYqriiSs6fn2qLrfUclHWZdxl7BApjoWmhZRJmZ2BoGredzrJmvuW96EwFny\r\n" + 
			"oMCmAr0oiklkQsiIydA7XoRzKL4AbMTqV3UNgOHzA76YYRP5p2enw8SYEjcxg7wk\r\n" + 
			"F5rU8XleWoqk7NvZgkaV/W+l/xWTCQufJD2Ja3T8THxHeQ0hc/sWo8lLZzO6QIlY\r\n" + 
			"71JFjkhIRjdmGCeFYi7czDef3vCpWSPzSgd5XSygk65Ws2VzRnJY6ARN/kijELTA\r\n" + 
			"WwIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";

}
