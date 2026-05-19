import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

export function middleware(request: NextRequest) {
  const token = request.cookies.get('jwt_token')?.value;

  // Si intentamos ir al dashboard, play o trial sin token, redirigimos al login
  if (!token && (
    request.nextUrl.pathname.startsWith('/dashboard') || 
    request.nextUrl.pathname.startsWith('/play') || 
    request.nextUrl.pathname.startsWith('/trial')
  )) {
    return NextResponse.redirect(new URL('/login', request.url));
  }

  // Si tenemos token y vamos al login, redirigimos al dashboard
  if (token && request.nextUrl.pathname.startsWith('/login')) {
    return NextResponse.redirect(new URL('/dashboard', request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ['/dashboard/:path*', '/play/:path*', '/trial/:path*', '/login'],
};
