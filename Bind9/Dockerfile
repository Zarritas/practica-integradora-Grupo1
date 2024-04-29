FROM internetsystemsconsortium/bind9

RUN apt update \
  && apt install -y \
  bind9-doc \
  dnsutils \
  geoip-bin \
  mariadb-server \
  net-tools

COPY configuration/named.conf.options /etc/bind/
COPY ./configuration/named.conf.local /etc/bind/
COPY ./configuration/db.jesus.jej /etc/bind/zones/

EXPOSE 53/tcp
EXPOSE 53/udp
EXPOSE 953/tcp

CMD ["/usr/sbin/named", "-g", "-c", "/etc/bind/named.conf", "-u", "bind"]