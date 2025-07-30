package dz_4_TV;

public class TV {
        private String производитель;
        private int размер; // в дюймах
        private boolean включен;

        // Конструктор
        public TV (String производитель, int размер) {
            this.производитель = производитель;
            this.размер = размер;
            this.включен =true; // по умолчанию выключен
        }

        // свойства
        public String getПроизводитель() {
            return производитель;
        }
        public int getРазмер() {
            return размер;
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
        public static class App {
            public static void main(String[] args) {
                // Создаем экземпляры телевизоров
                TV телевизор1 = new TV("Рубин", 32);
                TV телевизор2 = new TV("Горизонт", 27);
                телевизор1.включить();
                телевизор2.выключить();

                System.out.println("Производитель первого телевизора: " + телевизор1.getПроизводитель() +", размер экрана "+ телевизор1.getРазмер());
                System.out.println("Производитель второго телевизора: " + телевизор2.getПроизводитель() +", размер экрана "+ телевизор2.getРазмер());
            }
        }
        }

