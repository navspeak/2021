package javastuff;

public class SingletonResource {
   private SingletonResource(){};
   private static class Holder {
      static SingletonResource INSTANCE = new SingletonResource();
   }
   public static SingletonResource getInstance(){
      return Holder.INSTANCE;
   }

//   private static SingletonResource INSTANCE = null;
//   public static SingletonResource getInstance_DOUBLECHK(){
//      if (INSTANCE == null) {
//         synchronized (SingletonResource.class) {
//            if (INSTANCE == null) {
//               INSTANCE = new SingletonResource();
//            }
//         }
//      }
//      return INSTANCE;
//   }


   enum Resource { //lazily loaded by JVM

   }

}
