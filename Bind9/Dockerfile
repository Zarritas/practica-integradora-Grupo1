FROM internetsystemsconsortium/bind9:9.18

RUN apt update \
  && apt install -y \
  bind9-doc \
  dnsutils \
  geoip-bin \
  mariadb-server \
  net-tools

# Copy configuration files
COPY configuration/named.conf.options /etc/bind/
COPY configuration/named.conf.local /etc/bind/
COPY configuration/db.tienda.com /etc/bind/zones/
COPY configuration/db.172.19 /etc/bind/zones/

# Expose Ports
EXPOSE 53/tcp
EXPOSE 53/udp
EXPOSE 953/tcp

# Start the Name Service
CMD ["/usr/sbin/named", "-g", "-c", "/etc/bind/named.conf", "-u", "bind"]