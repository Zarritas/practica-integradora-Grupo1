$ORIGIN tienda.com.
$TTL    1d ; default expiration time (in seconds) of all RRs without their own TTL value
@       IN      SOA     ns1 root (
                  3      ; Serial
                  1d     ; Refresh
                  1h     ; Retry
                  1w     ; Expire
                  1h )   ; Negative Cache TTL

; name servers - NS records
     IN      NS      ns1

; name servers - A records
ns1             IN      A      172.19.0.6

mysql        IN      A      172.19.0.2
tomcat        IN      A      172.19.0.3
apache        IN      A      172.19.0.4
mongo        IN      A      172.19.0.5
vue        IN      A      172.19.0.18