package es.codeurjc.helloworldvscode.enumerate;

public enum Role {
        ROLE_ADMIN("ROLE_ADMIN"),
        ROLE_TEACHER("ROLE_TEACHER"),
        ROLE_STUDENT("ROLE_STUDENT");

        private final String rol;

        Role(String string) {
                this.rol = string;
        }

        @Override
        public String toString(){
            return this.rol;
        }
}