    public class TV {
        private String производитель;
        private int размер; // в дюймах
        private boolean включен;

        // Конструктор
        public TV (String производитель, int размер) {
            this.производитель = производитель;
            this.размер = размер;
            this.включен = false; // по умолчанию выключен
        }

        // свойства
        public String getПроизводитель() {
            return производитель;
        }
        public int getРазмер() {
            return размер;
        }

        public boolean isВключен() {
            return включен;
        }

        // действия (управление)
        public void включить() {
            включен = true;
            System.out.println("Телевизор " + производитель + " включен.");
        }

        public void выключить() {
            включен = false;
            System.out.println("Телевизор " + производитель + " выключен.");
        }
        }

